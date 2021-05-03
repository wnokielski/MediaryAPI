package com.mediary.Repositories;

import com.mediary.Models.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.mediary.Models.Entities.UserEntity, Long> {

    @Query("SELECT u from UserEntity u WHERE u.id=?1")
    UserEntity findByUserId(Integer userId);

    @Query("SELECT u from UserEntity u WHERE u.email=?1")
    UserEntity findByEmail(String email);

    UserEntity findUserEntitiesByEmail(String email);

    Optional<UserEntity> findById(Integer id);

    UserEntity getUserEntityById(Integer id);

}
