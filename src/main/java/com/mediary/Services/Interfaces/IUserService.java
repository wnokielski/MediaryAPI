package com.mediary.Services.Interfaces;

import com.mediary.Models.Entities.UserEntity;

import java.util.Optional;

public interface IUserService {
    int registerNewUser(UserEntity user);
    int updateUserDetails(UserEntity user);
    Optional<UserEntity> getUserById(int id);
    int updatePassword(String password, Integer id);
}
