package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.TestParameterEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestParameterRepository extends JpaRepository<TestParameterEntity, Long> {

    @Query("SELECT p from TestParameterEntity p WHERE p.testTypeById.id=?1")
    List<TestParameterEntity> findByTestTypeId(Integer testTypeId);
}
