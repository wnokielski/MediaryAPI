package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.Dtos.Request.AddStatisticRequestDto;
import com.mediary.Models.Dtos.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;

public interface IStatisticService {

    void addStatistics(List<AddStatisticRequestDto> statistics, Integer userId) throws Exception;

    StatisticEntity addStatistic(AddStatisticRequestDto statistic, Integer userId) throws Exception;

    List<GetStatisticDto> getStatisticsByUserAndStatisticType(Integer userId, Integer statisticTypeId) throws Exception;

    List<GetStatisticDto> statisticsToDtos(List<StatisticEntity> statisticEntities);

    GetStatisticDto statisticToDto(StatisticEntity statisticEntity);

}
