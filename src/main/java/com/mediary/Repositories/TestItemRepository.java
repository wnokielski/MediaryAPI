package com.mediary.Repositories;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.Entities.MedicalRecordEntity;
import com.mediary.Models.Entities.TestItemEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestItemRepository extends JpaRepository<TestItemEntity, Long> {
    void deleteById(Integer id);

    Collection<TestItemEntity> findAllByMedicalRecordById(MedicalRecordEntity medicalRecord);

    @Query("SELECT i from TestItemEntity i WHERE i.medicalRecordById.id=?1")
    List<TestItemEntity> findByMedicalRecordId(Integer medicalRecordId);

    TestItemEntity findById(Integer id);
}
