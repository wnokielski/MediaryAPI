package com.mediary.Services.Interfaces;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.Entities.UserEntity;

import java.util.Optional;

public interface IUserService {
    int registerNewUser(UserEntity user);
    int updateUserDetails(UserEntity user);
    UserDto getUserById(int id);
    int updatePassword(String newPassword, Integer id, String oldPassword);
}
