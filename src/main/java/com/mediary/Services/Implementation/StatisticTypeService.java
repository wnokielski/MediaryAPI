package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Response.GetStatisticTypeDto;
import com.mediary.Models.Entities.StatisticTypeEntity;
import com.mediary.Repositories.StatisticTypeRepository;
import com.mediary.Services.Interfaces.IStatisticTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticTypeService implements IStatisticTypeService {

    @Autowired
    StatisticTypeRepository statisticTypeRepository;

    @Override
    public List<GetStatisticTypeDto> getAllStatisticTypes() {
        var statisticTypes = statisticTypeRepository.findAll();
        var statisticTypeDtos = statisticTypesToDtos(statisticTypes);
        return statisticTypeDtos;
    }

    @Override
    public List<GetStatisticTypeDto> statisticTypesToDtos(List<StatisticTypeEntity> statisticTypeEntities) {
        var statisticTypeDtos = new ArrayList<GetStatisticTypeDto>();
        for (StatisticTypeEntity statisticTypeEntity : statisticTypeEntities) {
            var statisticTypeDto = statisticTypeToDto(statisticTypeEntity);
            statisticTypeDtos.add(statisticTypeDto);
        }
        return statisticTypeDtos;
    }

    @Override
    public GetStatisticTypeDto statisticTypeToDto(StatisticTypeEntity statisticTypeEntity) {
        var statisticTypeDto = new GetStatisticTypeDto();
        statisticTypeDto.setId(statisticTypeEntity.getId());
        statisticTypeDto.setName(statisticTypeEntity.getName());
        statisticTypeDto.setUnit(statisticTypeEntity.getUnit());
        return statisticTypeDto;
    }

}
