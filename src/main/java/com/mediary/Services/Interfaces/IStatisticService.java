package com.mediary.Services.Interfaces;

import java.util.List;
import java.util.Map;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import org.springframework.http.ResponseEntity;

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

        Long deleteStatisticByAuthHeaderAndStatisticId(String authHeader, Integer statisticId) throws EntityNotFoundException;

        Long deleteStatisticByUserAndStatisticId(UserEntity user, Integer statisticId) throws EntityNotFoundException;

        void updateStatisticByAuthHeaderAndStatisticId(String authHeader, Integer statisticId, Map<String, Object> updates)
                throws EntityNotFoundException;

        void updateStatisticByUserAndStatisticId(UserEntity user, Integer statisticId, Map<String, Object> updates) throws EntityNotFoundException;

        ResponseEntity<?> updateWholeStatisticByAuthHeaderAndStatisticId(AddStatisticDto statistic, Integer statisticId, String authHeader) throws EntityNotFoundException;

        ResponseEntity<?> updateWholeStatisticByUserAndStatisticId(AddStatisticDto statistic, Integer statisticId, UserEntity user) throws EntityNotFoundException;

}
