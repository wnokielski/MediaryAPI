package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.Dtos.Response.GetStatisticTypeDto;
import com.mediary.Models.Entities.StatisticTypeEntity;

public interface IStatisticTypeService {

    List<GetStatisticTypeDto> getAllStatisticTypes();

    List<GetStatisticTypeDto> statisticTypesToDtos(List<StatisticTypeEntity> statisticTypeEntities);

    GetStatisticTypeDto statisticTypeToDto(StatisticTypeEntity statisticTypeEntity);
}
