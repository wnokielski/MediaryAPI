package com.mediary.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import com.mediary.Models.Dtos.Request.AddStatisticRequestDto;
import com.mediary.Models.Dtos.Response.GetStatisticDto;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Repositories.StatisticRepository;
import com.mediary.Repositories.StatisticTypeRepository;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Interfaces.IStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService implements IStatisticService {

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatisticTypeRepository statisticTypeRepository;

    @Override
    public void addStatistics(List<AddStatisticRequestDto> statistics, Integer userId) throws Exception {
        for (AddStatisticRequestDto statistic : statistics) {
            addStatistic(statistic, userId);
        }
    }

    @Override
    public StatisticEntity addStatistic(AddStatisticRequestDto statistic, Integer userId) throws Exception {
        if (statistic.getValue().length() > 50) {
            throw new Exception("Too long value field");
        } else if (statistic.getDate() == null) {
            throw new Exception("Date filed can't be null");
        }
        StatisticEntity newStatistic = new StatisticEntity();
        newStatistic.setValue(statistic.getValue());
        newStatistic.setDate(statistic.getDate());
        var user = userRepository.findById(userId);
        newStatistic.setUserByUserid(user);

        var statisticType = statisticTypeRepository.findById(statistic.getStatisticTypeId());
        newStatistic.setStatistictypeByStatistictypeid(statisticType);
        statisticRepository.saveAndFlush(newStatistic);
        return newStatistic;
    }

    @Override
    public List<GetStatisticDto> getStatisticsByUserAndStatisticType(Integer userId, Integer statisticTypeId)
            throws Exception {
        if (!userRepository.existsById((long) userId)) {
            throw new Exception("User with specified ID doesn't exist!");
        } else if (!statisticTypeRepository.existsById((long) statisticTypeId)) {
            throw new Exception("Statistic type with specified ID doesn't exist!");
        }
        var statistics = statisticRepository.findByUserIdAndStatisticTypeId(userId, statisticTypeId);
        ArrayList<GetStatisticDto> statisticDtos = new ArrayList<GetStatisticDto>();
        for (StatisticEntity statistic : statistics) {
            var statisticDto = new GetStatisticDto();
            statisticDto.setId(statistic.getId());
            statisticDto.setValue(statistic.getValue());
            statisticDto.setDate(statistic.getDate());
            statisticDto.setStatisticTypeName(statistic.getStatistictypeByStatistictypeid().getName());
            statisticDtos.add(statisticDto);
        }
        return statisticDtos;
    }
}
