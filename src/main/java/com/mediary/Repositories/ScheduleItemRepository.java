package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.ScheduleItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItemEntity, Long> {

    @Query("SELECT s from ScheduleItemEntity s WHERE s.userById.id=?1")
    List<ScheduleItemEntity> findByUserId(Integer id);
}