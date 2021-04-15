package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.Dtos.Response.GetStatisticTypeDto;
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
        var statisticTypeDtos = new ArrayList<GetStatisticTypeDto>();
        for (StatisticTypeEntity statisticType : statisticTypes) {
            var statisticTypeDto = new GetStatisticTypeDto();
            statisticTypeDto.setId(statisticType.getId());
            statisticType.setName(statisticType.getName());
            statisticTypeDtos.add(statisticTypeDto);
        }
        return statisticTypeDtos;
    }

}
