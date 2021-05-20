package com.mediary.Repositories;

import java.util.Collection;
import java.util.List;

import com.mediary.Models.Entities.FileEntity;

import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    void deleteById(Integer id);

    Collection<FileEntity> findAllByTestResultById(TestResultEntity testResult);

    @Query("SELECT f from FileEntity f WHERE f.testResultById.id=?1")
    List<FileEntity> findByTestResultId(Integer testResultId);

    FileEntity findById(Integer id);
}
