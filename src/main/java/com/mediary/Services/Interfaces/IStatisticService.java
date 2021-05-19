package com.mediary.Services.Interfaces;

import java.util.List;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

public interface IStatisticService {

        void addStatistcsByAuthHeader(List<AddStatisticDto> statistics, String authHeader)
                        throws EntityNotFoundException, IncorrectFieldException;

        void addStatistics(List<AddStatisticDto> statistics, UserEntity user)
                        throws EntityNotFoundException, IncorrectFieldException;

        void addStatistic(AddStatisticDto statistic, UserEntity user)
                        throws EntityNotFoundException, IncorrectFieldException;

        List<GetStatisticDto> getStatisticsByAuthHeaderAndStatisticType(String authHeader, Integer statisticTypeId)
                        throws EntityNotFoundException;

        List<GetStatisticDto> getStatisticsByUserAndStatisticType(Integer userId, Integer statisticTypeId)
                        throws EntityNotFoundException;

        List<GetStatisticDto> statisticsToDtos(List<StatisticEntity> statisticEntities);

        GetStatisticDto statisticToDto(StatisticEntity statisticEntity);

        List<GetStatisticDto> getStatisticsByAuthHeaderAndStatisticTypeAndDate(String authHeader, Integer statisticTypeId, String dateFrom, String dateTo)
                throws EntityNotFoundException;

        List<GetStatisticDto> getStatisticsByUserAndStatisticTypeAndDate(Integer userId, Integer statisticTypeId, String dateFrom, String dateTo)
                throws EntityNotFoundException;

}
