package com.mediary.Repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.mediary.Models.Entities.StatisticEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {

    @Query("SELECT s from StatisticEntity s WHERE s.userById.id=?1 AND s.statisticTypeById.id=?2 ORDER BY s.date")
    List<StatisticEntity> findByUserIdAndStatisticTypeId(Integer userId, Integer statisticTypeId);

    List<StatisticEntity> findByUserByIdAndStatisticTypeByIdAndDateBetween(Optional<com.mediary.Models.Entities.UserEntity> user,
                                                                           com.mediary.Models.Entities.StatisticTypeEntity statisticType,
                                                                           Timestamp dateFrom, Timestamp dateTo);

    StatisticEntity findByUserByIdAndId(com.mediary.Models.Entities.UserEntity user, Integer statisticId);

    Long deleteById(Integer statisticId);
}
