package com.mediary.Services.Implementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Request.ScheduleItemUpdateDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Repositories.ScheduleItemRepository;
import com.mediary.Repositories.ScheduleItemTypeRepository;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.EntityDoesNotBelongToUser;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IScheduleItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ScheduleItemService implements IScheduleItemService {

    @Autowired
    ScheduleItemRepository scheduleItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    ScheduleItemTypeRepository scheduleItemTypeRepository;

    @Autowired
    ScheduleItemTypeService scheduleItemTypeService;

    @Override
    public void addScheduleItemsByAuthHeader(List<AddScheduleItemDto> scheduleItemDtos, String authHeader)
            throws EntityNotFoundException, IncorrectFieldException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        addScheduleItems(scheduleItemDtos, user);
    }

    @Override
    public void addScheduleItems(List<AddScheduleItemDto> scheduleItemDtos, UserEntity user)
            throws IncorrectFieldException, EntityNotFoundException {
        for (AddScheduleItemDto scheduleItemDto : scheduleItemDtos) {
            addScheduleItem(scheduleItemDto, user);
        }
    }

    @Override
    public void addScheduleItem(AddScheduleItemDto scheduleItemDto, UserEntity user)
            throws IncorrectFieldException, EntityNotFoundException {
        if (scheduleItemDto.getTitle().length() > 50 || scheduleItemDto.getTitle() == "") {
            log.warn("Title field is incorrect");
            throw new IncorrectFieldException("Title field is incorrect");
        } else if (scheduleItemDto.getDate() == null) {
            log.warn("Date field is required");
            throw new IncorrectFieldException("Date field is required");
        } else if (scheduleItemDto.getPlace().length() > 50 || scheduleItemDto.getPlace() == "") {
            log.warn("Place field is incorrect");
            throw new IncorrectFieldException("Place field is incorrect");
        } else if (scheduleItemDto.getAddress().length() > 50) {
            log.warn("Address field is too long");
            throw new IncorrectFieldException("Address field is too long");
        } else if (scheduleItemDto.getNote().length() > 200) {
            log.warn("Note field is too long");
            throw new IncorrectFieldException("Note field is too long");
        } else if (scheduleItemDto.getScheduleItemTypeId() == null) {
            log.warn("Schedule item type is required");
            throw new IncorrectFieldException("Schedule item type is required");
        } else if (scheduleItemTypeRepository.findById(scheduleItemDto.getScheduleItemTypeId()) == null) {
            log.warn("Schedule item type doesn't exist");
            throw new EntityNotFoundException("Schedule item type with specified id doesn't exist");
        } else {
            ScheduleItemEntity scheduleItemEntity = new ScheduleItemEntity();
            scheduleItemEntity.setTitle(scheduleItemDto.getTitle());
            scheduleItemEntity.setDate(scheduleItemDto.getDate());
            scheduleItemEntity.setPlace(scheduleItemDto.getPlace());
            scheduleItemEntity.setAddress(scheduleItemDto.getAddress());
            scheduleItemEntity.setNote(scheduleItemDto.getNote());
            scheduleItemEntity.setUserById(user);

            var scheduleItemType = scheduleItemTypeRepository.findById(scheduleItemDto.getScheduleItemTypeId());
            scheduleItemEntity.setScheduleItemTypeById(scheduleItemType);
            scheduleItemRepository.saveAndFlush(scheduleItemEntity);
        }
    }

    @Override
    public List<GetScheduleItemDto> getScheduleItemsByAuthHeader(String authHeader) throws EntityNotFoundException {
        var user = userService.getUserByAuthHeader(authHeader);
        var scheduleItems = scheduleItemRepository.findByUserId(user.getId());
        List<GetScheduleItemDto> scheduleItemDtos = scheduleItemsToDtos(scheduleItems);
        return scheduleItemDtos;

    }

    @Override
    public List<GetScheduleItemDto> scheduleItemsToDtos(List<ScheduleItemEntity> scheduleItemEntities) {
        List<GetScheduleItemDto> schedulItemDtos = new ArrayList<GetScheduleItemDto>();

        for (ScheduleItemEntity scheduleItemEntity : scheduleItemEntities) {
            var scheduleItemDto = scheduleItemToDto(scheduleItemEntity);
            schedulItemDtos.add(scheduleItemDto);
        }
        return schedulItemDtos;
    }

    @Override
    public GetScheduleItemDto scheduleItemToDto(ScheduleItemEntity scheduleItemEntity) {
        GetScheduleItemDto scheduleItemDto = new GetScheduleItemDto();
        scheduleItemDto.setId(scheduleItemEntity.getId());
        scheduleItemDto.setTitle(scheduleItemEntity.getTitle());
        scheduleItemDto.setNote(scheduleItemEntity.getNote());
        scheduleItemDto.setPlace(scheduleItemEntity.getPlace());
        scheduleItemDto.setAddress(scheduleItemEntity.getAddress());
        scheduleItemDto.setDate(scheduleItemEntity.getDate());

        var scheduleItemType = scheduleItemEntity.getScheduleItemTypeById();
        scheduleItemDto.setScheduleItemTypeId(scheduleItemType.getId());

        return scheduleItemDto;
    }

    @Override
    @Transactional
    public int deleteScheduleItem(UserDto user, Integer scheduleItemId) {
        ScheduleItemEntity scheduleItem = scheduleItemRepository.findById(scheduleItemId);
        if (scheduleItem != null) {
            if (scheduleItem.getUserById().getId().equals(user.getId())) {
                scheduleItemRepository.deleteById(scheduleItemId);
                return Const.scheduleItemDeletionSuccess;
            }
            return Const.scheduleItemDeletionError;
        }
        return Const.scheduleItemDoesNotExist;
    }

    @Override
    public List<GetScheduleItemDto> getScheduleItemByAuthHeadeAndDate(String authHeader, String dateFrom, String dateTo)
            throws EntityNotFoundException {
        UserEntity user = userService.getUserByAuthHeader(authHeader);
        if (user != null) {
            var scheduleItems = scheduleItemRepository.findByUserByIdAndDateBetween(java.util.Optional.of(user),
                    Timestamp.valueOf(dateFrom + " 00:00:00"), Timestamp.valueOf(dateTo + " 23:59:59"));
            ArrayList<GetScheduleItemDto> scheduleItemDtos = (ArrayList<GetScheduleItemDto>) scheduleItemsToDtos(
                    scheduleItems);
            return scheduleItemDtos;
        } else {
            throw new EntityNotFoundException("User doesn't exist.");
        }
    }

    @Override
    public void updateScheduleItem(ScheduleItemUpdateDto scheduleItemDto, UserDto user, Integer scheduleItemId)
            throws IncorrectFieldException, EntityNotFoundException, EntityDoesNotBelongToUser {
        ScheduleItemEntity updatedScheduleItem = scheduleItemRepository.findById(scheduleItemId);
        if (updatedScheduleItem != null) {
            if (updatedScheduleItem.getUserById().getId().equals(user.getId())) {
                if (scheduleItemDto.getTitle().length() > 50 || scheduleItemDto.getTitle() == "") {
                    log.warn("Title field is incorrect");
                    throw new IncorrectFieldException("Title field is incorrect");
                } else if (scheduleItemDto.getDate() == null) {
                    log.warn("Date field is required");
                    throw new IncorrectFieldException("Date field is required");
                } else if (scheduleItemDto.getPlace().length() > 50 || scheduleItemDto.getPlace() == "") {
                    log.warn("Place field is incorrect");
                    throw new IncorrectFieldException("Place field is incorrect");
                } else if (scheduleItemDto.getAddress().length() > 50) {
                    log.warn("Address field is too long");
                    throw new IncorrectFieldException("Address field is too long");
                } else if (scheduleItemDto.getNote().length() > 200) {
                    log.warn("Note field is too long");
                    throw new IncorrectFieldException("Note field is too long");
                } else if (scheduleItemDto.getScheduleItemTypeId() == null) {
                    log.warn("Schedule item type is required");
                    throw new IncorrectFieldException("Schedule item type is required");
                } else if (scheduleItemTypeRepository.findById(scheduleItemDto.getScheduleItemTypeId()) == null) {
                    log.warn("Schedule item type doesn't exist");
                    throw new EntityNotFoundException("Schedule item type with specified id doesn't exist");
                } else {
                    if (!scheduleItemDto.getTitle().equals(updatedScheduleItem.getTitle()))
                        updatedScheduleItem.setTitle(scheduleItemDto.getTitle());
                    if (!scheduleItemDto.getDate().equals(updatedScheduleItem.getDate()))
                        updatedScheduleItem.setDate(scheduleItemDto.getDate());
                    if (!scheduleItemDto.getPlace().equals(updatedScheduleItem.getPlace()))
                        updatedScheduleItem.setPlace(scheduleItemDto.getPlace());
                    if (!scheduleItemDto.getAddress().equals(updatedScheduleItem.getAddress()))
                        updatedScheduleItem.setAddress(scheduleItemDto.getAddress());
                    if (!scheduleItemDto.getNote().equals(updatedScheduleItem.getNote()))
                        updatedScheduleItem.setNote(scheduleItemDto.getNote());
                    if (!scheduleItemDto.getScheduleItemTypeId().equals(updatedScheduleItem.getScheduleItemTypeById()))
                        updatedScheduleItem.setScheduleItemTypeById(
                                scheduleItemTypeRepository.findById(scheduleItemDto.getScheduleItemTypeId()));
                    scheduleItemRepository.save(updatedScheduleItem);

                }
            } else {
                log.warn("Schedule item doesn't belong to this user!");
                throw new EntityDoesNotBelongToUser("Schedule item doesn't belong to this user!");
            }
        } else {
            log.warn("Schedule item doesn't exist");
            throw new EntityNotFoundException("Schedule item with specified id doesn't exist");
        }

    }

}
