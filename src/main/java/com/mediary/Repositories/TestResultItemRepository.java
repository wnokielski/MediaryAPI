package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.TestResultItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestResultItemRepository extends JpaRepository<TestResultItemEntity, Long> {

    @Query("SELECT i from TestResultItemEntity i WHERE i.testresultByTestresultid.id=?1")
    List<TestResultItemEntity> findByTestResultId(Integer testResultId);
}
