package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.TestResultEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResultEntity, Long> {

    TestResultEntity findById(Integer id);

    List<TestResultEntity> findByUserByUserid(Integer id);
}
