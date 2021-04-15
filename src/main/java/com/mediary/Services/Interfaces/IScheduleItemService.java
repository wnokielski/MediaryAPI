package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.Dtos.Request.AddScheduleItemDto;
import com.mediary.Models.Dtos.Response.GetScheduleItemDto;
import com.mediary.Models.Entities.ScheduleItemEntity;

public interface IScheduleItemService {

    void addScheduleItems(List<AddScheduleItemDto> scheduleItemDtos, Integer userId) throws Exception;

    void addScheduleItem(AddScheduleItemDto scheduleItemDto, Integer userId) throws Exception;

    List<GetScheduleItemDto> getScheduleItemsByUser(Integer userId);

    List<GetScheduleItemDto> scheduleItemsToDtos(List<ScheduleItemEntity> scheduleItemEntities);

    GetScheduleItemDto scheduleItemToDto(ScheduleItemEntity scheduleItemEntity);
}
