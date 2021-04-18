package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddScheduleItemDto;
import com.mediary.Models.DTOs.Response.GetScheduleItemDto;
import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

public interface IScheduleItemService {

    void addScheduleItems(List<AddScheduleItemDto> scheduleItemDtos, Integer userId)
            throws EntityNotFoundException, IncorrectFieldException;

    void addScheduleItem(AddScheduleItemDto scheduleItemDto, Integer userId)
            throws EntityNotFoundException, IncorrectFieldException;

    List<GetScheduleItemDto> getScheduleItemsByUser(Integer userId) throws EntityNotFoundException;

    List<GetScheduleItemDto> scheduleItemsToDtos(List<ScheduleItemEntity> scheduleItemEntities);

    GetScheduleItemDto scheduleItemToDto(ScheduleItemEntity scheduleItemEntity);
}
