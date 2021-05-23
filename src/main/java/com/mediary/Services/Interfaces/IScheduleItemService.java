package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Request.ScheduleItemUpdateDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Exceptions.EntityDoesNotBelongToUser;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

public interface IScheduleItemService {

        void addScheduleItemsByAuthHeader(List<AddScheduleItemDto> scheduleItemDtos, String authHeader)
                        throws EntityNotFoundException, IncorrectFieldException;

        void addScheduleItems(List<AddScheduleItemDto> scheduleItemDtos, UserEntity user)
                        throws EntityNotFoundException, IncorrectFieldException;

        void addScheduleItem(AddScheduleItemDto scheduleItemDto, UserEntity user)
                        throws EntityNotFoundException, IncorrectFieldException;

        List<GetScheduleItemDto> getScheduleItemsByAuthHeader(String authHeader) throws EntityNotFoundException;

        List<GetScheduleItemDto> scheduleItemsToDtos(List<ScheduleItemEntity> scheduleItemEntities);

        GetScheduleItemDto scheduleItemToDto(ScheduleItemEntity scheduleItemEntity);

        int deleteScheduleItem(UserDto userId, Integer scheduleItemId);

        List<GetScheduleItemDto> getScheduleItemByAuthHeadeAndDate(String authHeader, String dateFrom, String dateTo) throws EntityNotFoundException;

        void updateScheduleItem(ScheduleItemUpdateDto scheduleItem, UserDto user, Integer scheduleItemId)
                throws IncorrectFieldException, EntityNotFoundException, EntityDoesNotBelongToUser;
}
