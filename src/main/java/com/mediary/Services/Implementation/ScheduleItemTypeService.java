package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.Dtos.Response.GetScheduleItemTypeDto;
import com.mediary.Models.Entities.ScheduleItemTypeEntity;
import com.mediary.Repositories.ScheduleItemTypeRepository;
import com.mediary.Services.Interfaces.IScheduleItemTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleItemTypeService implements IScheduleItemTypeService {

    @Autowired
    ScheduleItemTypeRepository scheduleItemTypeRepository;

    public List<GetScheduleItemTypeDto> getAllScheduleItemTypes() {
        var scheduleItemTypes = scheduleItemTypeRepository.findAll();
        var scheduleItemTypeDtos = scheduleItemTypesToDtos(scheduleItemTypes);
        return scheduleItemTypeDtos;
    }

    public List<GetScheduleItemTypeDto> scheduleItemTypesToDtos(List<ScheduleItemTypeEntity> scheduleItemTypeEntities) {
        var scheduleItemTypeDtos = new ArrayList<GetScheduleItemTypeDto>();

        for (ScheduleItemTypeEntity scheduleItemTypeEntity : scheduleItemTypeEntities) {
            var scheduleItemTypeDto = scheduleItemTypeToDto(scheduleItemTypeEntity);
            scheduleItemTypeDtos.add(scheduleItemTypeDto);
        }
        return scheduleItemTypeDtos;
    }

    public GetScheduleItemTypeDto scheduleItemTypeToDto(ScheduleItemTypeEntity scheduleItemTypeEntity) {
        var scheduleItemTypeDto = new GetScheduleItemTypeDto();
        scheduleItemTypeDto.setId(scheduleItemTypeEntity.getId());
        scheduleItemTypeDto.setName(scheduleItemTypeEntity.getName());
        return scheduleItemTypeDto;
    }
}
