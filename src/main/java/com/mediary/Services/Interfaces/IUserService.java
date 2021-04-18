package com.mediary.Services.Interfaces;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Models.DTOs.JwtRequest;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    int registerNewUser(UserRegisterDto user);

    int updateUserDetails(UserUpdateDto user, Integer userId);

    UserDto getUserById(int id);

    int updatePassword(ChangePasswordDto passwordDto, Integer id);

    ResponseEntity<?> authenticateUser(JwtRequest authenticationRequest);
}
