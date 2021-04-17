package com.mediary.Repositories;

import com.mediary.Models.Entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<com.mediary.Models.Entities.UserEntity, Long> {

    UserEntity findById(Integer userId);
}
