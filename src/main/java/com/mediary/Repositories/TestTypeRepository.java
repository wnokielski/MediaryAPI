package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.TestTypeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTypeRepository extends JpaRepository<TestTypeEntity, Long> {

    List<TestTypeEntity> findAll();

    TestTypeEntity findById(Integer id);
}
