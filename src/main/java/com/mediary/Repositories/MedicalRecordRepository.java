package com.mediary.Repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.mediary.Models.Entities.MedicalRecordEntity;

import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Models.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, Long> {

    void deleteById(Integer id);

    MedicalRecordEntity findById(Integer id);

    @Query("SELECT t from MedicalRecordEntity t WHERE t.userById.id=?1")
    List<MedicalRecordEntity> findByUserId(Integer id);

    List<MedicalRecordEntity> findByUserByIdAndDateOfTheTestBetweenOrderByDateOfTheTest(Optional<UserEntity> user, Timestamp dateFrom, Timestamp dateTo);
}
