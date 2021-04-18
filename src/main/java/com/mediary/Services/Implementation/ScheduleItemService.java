package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Repositories.ScheduleItemRepository;
import com.mediary.Repositories.ScheduleItemTypeRepository;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IScheduleItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleItemService implements IScheduleItemService {

    @Autowired
    ScheduleItemRepository scheduleItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleItemTypeRepository scheduleItemTypeRepository;

    @Autowired
    ScheduleItemTypeService scheduleItemTypeService;

    @Override
    public void addScheduleItems(List<AddScheduleItemDto> scheduleItemDtos, Integer userId)
            throws IncorrectFieldException, EntityNotFoundException {
        for (AddScheduleItemDto scheduleItemDto : scheduleItemDtos) {
            addScheduleItem(scheduleItemDto, userId);
        }
    }

    @Override
    public void addScheduleItem(AddScheduleItemDto scheduleItemDto, Integer userId)
            throws IncorrectFieldException, EntityNotFoundException {
        if (scheduleItemDto.getTitle().length() > 30 || scheduleItemDto.getTitle() == "") {
            log.warn("Title field is incorrect");
            throw new IncorrectFieldException("Title field is incorrect");
        } else if (scheduleItemDto.getDate() == null) {
            log.warn("Date field is required");
            throw new IncorrectFieldException("Date field is required");
        } else if (scheduleItemDto.getPlace().length() > 30 || scheduleItemDto.getPlace() == "") {
            log.warn("Place field is incorrect");
            throw new IncorrectFieldException("Place field is incorrect");
        } else if (scheduleItemDto.getAddress().length() > 50) {
            log.warn("Address field is too long");
            throw new IncorrectFieldException("Address field is too long");
        } else if (scheduleItemDto.getNote().length() > 100) {
            log.warn("Note field is too long");
            throw new IncorrectFieldException("Note field is too long");
        } else if (scheduleItemDto.getScheduleItemTypeId() == null) {
            log.warn("Schedule item type is required");
            throw new IncorrectFieldException("Schedule item type is required");
        } else if (userRepository.findByUserId(userId) == null) {
            log.warn("User doesn't exist");
            throw new EntityNotFoundException("User with specified id doesn't exist");
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

            var user = userRepository.findByUserId(userId);
            scheduleItemEntity.setUserByUserid(user);

            var scheduleItemType = scheduleItemTypeRepository.findById(scheduleItemDto.getScheduleItemTypeId());
            scheduleItemEntity.setScheduleitemtypeByScheduleitemtypeid(scheduleItemType);
            scheduleItemRepository.saveAndFlush(scheduleItemEntity);
        }
    }

    @Override
    public List<GetScheduleItemDto> getScheduleItemsByUser(Integer userId) throws EntityNotFoundException {
        if (userRepository.findByUserId(userId) == null) {
            throw new EntityNotFoundException("User with specified id doesn't exist");
        } else {
            var scheduleItems = scheduleItemRepository.findByUserId(userId);
            List<GetScheduleItemDto> scheduleItemDtos = scheduleItemsToDtos(scheduleItems);
            return scheduleItemDtos;
        }

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

        var scheduleItemType = scheduleItemEntity.getScheduleitemtypeByScheduleitemtypeid();
        scheduleItemDto.setScheduleItemType(scheduleItemTypeService.scheduleItemTypeToDto(scheduleItemType));

        return scheduleItemDto;
    }
}
