package com.mediary.Repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mediary.Models.Entities.ScheduleItemEntity;

import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Models.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItemEntity, Long> {

    @Query("SELECT s from ScheduleItemEntity s WHERE s.userById.id=?1")
    List<ScheduleItemEntity> findByUserId(Integer id);

    ScheduleItemEntity findById(Integer scheduleItemId);

    List<ScheduleItemEntity> findByUserByIdAndDateBetweenOrderByDate(Optional<UserEntity> user, Timestamp dateFrom, Timestamp dateTo);

//    @Query(value = "SELECT * FROM ScheduleItemEntity WHERE UserId = ?1 AND Date >= ?2 AND Date <= ?3 ORDER BY Date", nativeQuery = true)
//    List<ScheduleItemEntity> findByUserByIdAndDateBetween(Optional<Integer> userId, Timestamp dateFrom, Timestamp dateTo);

    void deleteById(Integer id);
}