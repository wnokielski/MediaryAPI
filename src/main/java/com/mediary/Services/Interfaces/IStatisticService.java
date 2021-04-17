package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;

public interface IStatisticService {

    void addStatistics(List<AddStatisticDto> statistics, Integer userId) throws Exception;

    void addStatistic(AddStatisticDto statistic, Integer userId) throws Exception;

    List<GetStatisticDto> getStatisticsByUserAndStatisticType(Integer userId, Integer statisticTypeId) throws Exception;

    List<GetStatisticDto> statisticsToDtos(List<StatisticEntity> statisticEntities);

    GetStatisticDto statisticToDto(StatisticEntity statisticEntity);

}
