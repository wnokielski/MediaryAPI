package com.mediary.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<com.mediary.Models.Entities.UsersEntity, Long> {
}
