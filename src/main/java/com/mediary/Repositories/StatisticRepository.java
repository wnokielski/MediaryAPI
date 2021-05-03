package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.StatisticEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {

    List<StatisticEntity> findByUserById(Integer id);

    @Query("SELECT s from StatisticEntity s WHERE s.userById.id=?1 AND s.statisticTypeById.id=?2 ORDER BY s.date")
    List<StatisticEntity> findByUserIdAndStatisticTypeId(Integer userId, Integer statisticTypeId);
}
