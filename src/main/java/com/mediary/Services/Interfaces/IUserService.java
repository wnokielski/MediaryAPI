package com.mediary.Services.Interfaces;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Models.DTOs.JwtRequest;
import com.mediary.Services.Exceptions.User.*;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    int updateUserDetails(UserUpdateDto user, Integer userId);

    UserDto getUserById(int id);

    int updatePassword(ChangePasswordDto passwordDto, Integer id);

    ResponseEntity<?> authenticateUser(JwtRequest authenticationRequest);

    ResponseEntity<?> signInAfterRegistration(UserRegisterDto user) throws EmailAlreadyUsedException, FullNameToLongException, EmailToLongException, UserDoesNotExist, PasswordToLongException;

    ResponseEntity<?> refreshToken(String authHeader);
}
