package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Response.GetScheduleItemTypeDto;
import com.mediary.Models.Entities.ScheduleItemTypeEntity;

public interface IScheduleItemTypeService {
    List<GetScheduleItemTypeDto> getAllScheduleItemTypes();

    List<GetScheduleItemTypeDto> scheduleItemTypesToDtos(List<ScheduleItemTypeEntity> ScheduleItemTypeEntities);

    GetScheduleItemTypeDto scheduleItemTypeToDto(ScheduleItemTypeEntity scheduleItemTypeEntity);

    void deleteScheduleItemType(Integer id);
}
