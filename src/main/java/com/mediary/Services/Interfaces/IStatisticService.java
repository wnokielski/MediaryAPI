package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

public interface IStatisticService {

        void addStatistics(List<AddStatisticDto> statistics, Integer userId)
                        throws EntityNotFoundException, IncorrectFieldException;

        void addStatistic(AddStatisticDto statistic, Integer userId)
                        throws EntityNotFoundException, IncorrectFieldException;

        List<GetStatisticDto> getStatisticsByUserAndStatisticType(Integer userId, Integer statisticTypeId)
                        throws EntityNotFoundException;

        List<GetStatisticDto> statisticsToDtos(List<StatisticEntity> statisticEntities);

        GetStatisticDto statisticToDto(StatisticEntity statisticEntity);

}
