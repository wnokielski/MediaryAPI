package com.mediary.Repositories;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.Entities.FileEntity;

import com.mediary.Models.Entities.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    void deleteById(Integer id);

    Collection<FileEntity> findAllByMedicalRecordById(MedicalRecordEntity medicalRecord);

    @Query("SELECT f from FileEntity f WHERE f.medicalRecordById.id=?1")
    List<FileEntity> findByMedicalRecordId(Integer medicalRecordId);

    FileEntity findById(Integer id);
}
