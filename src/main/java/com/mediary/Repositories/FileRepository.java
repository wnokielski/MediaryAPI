package com.mediary.Repositories;

import java.util.List;

import com.mediary.Models.Entities.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Query("SELECT f from FileEntity f WHERE f.testresultByTestresultid.id=?1")
    List<FileEntity> findByTestResultId(Integer testResultId);

    FileEntity findById(Integer id);
}