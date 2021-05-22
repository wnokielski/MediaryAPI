package com.mediary.Repositories;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestResultItemRepository extends JpaRepository<TestResultItemEntity, Long> {

    void deleteById(Integer id);

    Collection<TestResultItemEntity> findAllByTestResultById(TestResultEntity testResult);

    @Query("SELECT i from TestResultItemEntity i WHERE i.testResultById.id=?1")
    List<TestResultItemEntity> findByTestResultId(Integer testResultId);

    TestResultItemEntity findById(Integer id);


}
