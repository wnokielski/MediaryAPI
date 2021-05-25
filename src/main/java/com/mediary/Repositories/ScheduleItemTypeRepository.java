package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.ScheduleItemTypeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleItemTypeRepository extends JpaRepository<ScheduleItemTypeEntity, Long> {
    List<ScheduleItemTypeEntity> findAll();

    ScheduleItemTypeEntity findById(Integer id);

    void deleteById(Integer id);
}
