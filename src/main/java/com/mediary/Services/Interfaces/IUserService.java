package com.mediary.Services.Interfaces;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Models.DTOs.JwtRequest;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.EnumConversionException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Exceptions.User.*;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    int updateUserDetails(UserUpdateDto user, String authHeader)
            throws EntityNotFoundException, EnumConversionException, IncorrectFieldException;

    UserDto getUserDetails(String authHeader) throws EntityNotFoundException;

    int updatePassword(ChangePasswordDto passwordDto, String authHeader) throws EntityNotFoundException;

    ResponseEntity<?> authenticateUser(JwtRequest authenticationRequest);

    ResponseEntity<?> signInAfterRegistration(UserRegisterDto user) throws EmailAlreadyUsedException,
            FullNameToLongException, EmailToLongException, UserDoesNotExist, PasswordToLongException;

    ResponseEntity<?> refreshToken(String authHeader);

    UserEntity getUserByAuthHeader(String authHeader) throws EntityNotFoundException;
}
