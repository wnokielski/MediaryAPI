package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.TestResultEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestResultRepository extends JpaRepository<TestResultEntity, Long> {

    TestResultEntity findById(Integer id);

    @Query("SELECT t from TestResultEntity t WHERE t.userById.id=?1")
    List<TestResultEntity> findByUserId(Integer id);
}
