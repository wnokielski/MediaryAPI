package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.DTOs.Request.AddStatisticDto;
import com.mediary.Models.DTOs.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Repositories.StatisticRepository;
import com.mediary.Repositories.StatisticTypeRepository;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Interfaces.IStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticService implements IStatisticService {

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatisticTypeRepository statisticTypeRepository;

    @Override
    public void addStatistics(List<AddStatisticDto> statistics, Integer userId)
            throws IncorrectFieldException, EntityNotFoundException {
        for (AddStatisticDto statistic : statistics) {
            addStatistic(statistic, userId);
        }
    }

    @Override
    public void addStatistic(AddStatisticDto statistic, Integer userId)
            throws IncorrectFieldException, EntityNotFoundException {
        if (statistic.getValue().length() > 50) {
            log.warn("Value field exceeds allowed length");
            throw new IncorrectFieldException("Value field exceeds allowed length");
        } else if (statistic.getDate() == null) {
            log.warn("Date field is required");
            throw new IncorrectFieldException("Date field is required");
        }
        StatisticEntity newStatistic = new StatisticEntity();
        newStatistic.setValue(statistic.getValue());
        newStatistic.setDate(statistic.getDate());
        var user = userRepository.findByUserId(userId);
        if (user != null) {
            newStatistic.setUserByUserid(user);
        } else {
            throw new EntityNotFoundException("User with specified ID doesn't exist");
        }

        var statisticType = statisticTypeRepository.findById(statistic.getStatisticTypeId());
        if (statisticType != null) {
            newStatistic.setStatistictypeByStatistictypeid(statisticType);
        } else {
            throw new EntityNotFoundException("Statistic type with specified ID doesn't exist!");
        }

        statisticRepository.saveAndFlush(newStatistic);
    }

    @Override
    public List<GetStatisticDto> getStatisticsByUserAndStatisticType(Integer userId, Integer statisticTypeId)
            throws EntityNotFoundException {
        if (userRepository.findById(userId) == null) {
            log.warn("User with specified ID doesn't exist!");
            throw new EntityNotFoundException("User with specified ID doesn't exist!");
        } else if (statisticTypeRepository.findById(statisticTypeId) == null) {
            log.warn("User with specified ID doesn't exist!");
            throw new EntityNotFoundException("Statistic type with specified ID doesn't exist!");
        }
        var statistics = statisticRepository.findByUserIdAndStatisticTypeId(userId, statisticTypeId);
        ArrayList<GetStatisticDto> statisticDtos = (ArrayList<GetStatisticDto>) statisticsToDtos(statistics);
        return statisticDtos;
    }

    @Override
    public List<GetStatisticDto> statisticsToDtos(List<StatisticEntity> statisticEntities) {
        ArrayList<GetStatisticDto> statisticDtos = new ArrayList<GetStatisticDto>();
        for (StatisticEntity statisticEntity : statisticEntities) {
            var statisticDto = statisticToDto(statisticEntity);
            statisticDtos.add(statisticDto);
        }
        return statisticDtos;
    }

    @Override
    public GetStatisticDto statisticToDto(StatisticEntity statisticEntity) {
        var statisticDto = new GetStatisticDto();
        statisticDto.setId(statisticEntity.getId());
        statisticDto.setValue(statisticEntity.getValue());
        statisticDto.setDate(statisticEntity.getDate());
        statisticDto.setStatisticTypeName(statisticEntity.getStatistictypeByStatistictypeid().getName());
        return statisticDto;
    }
}
