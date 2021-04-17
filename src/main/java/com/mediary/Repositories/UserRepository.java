package com.mediary.Repositories;

import com.mediary.Models.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.mediary.Models.Entities.UserEntity, Long> {
    UserEntity findUserEntitiesByUsername(String userName);
    UserEntity findUserEntitiesByEmail(String email);
    Optional<UserEntity> findById(Integer id);
    UserEntity getUserEntityById(Integer id);
}
