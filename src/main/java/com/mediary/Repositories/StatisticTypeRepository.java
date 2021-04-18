package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.StatisticTypeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticTypeRepository extends JpaRepository<StatisticTypeEntity, Long> {

    List<StatisticTypeEntity> findAll();

    StatisticTypeEntity findById(Integer id);

}
