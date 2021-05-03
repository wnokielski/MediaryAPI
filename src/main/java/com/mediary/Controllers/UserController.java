package com.mediary.Controllers;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.EnumConversionException;
import com.mediary.Services.Exceptions.IncorrectFieldException;
import com.mediary.Services.Exceptions.User.*;
import com.mediary.Services.Implementation.UserService;
import com.mediary.Models.DTOs.JwtRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> register(@RequestBody UserRegisterDto user)
            throws PasswordToLongException, EmailAlreadyUsedException, EmailToLongException, FullNameToLongException {
        return userService.signInAfterRegistration(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUserDetails(@RequestHeader("Authorization") String authHeader, @RequestBody UserUpdateDto user)
            throws EmailAlreadyUsedException, FullNameToLongException, EmailToLongException, UserDoesNotExist,
            EntityNotFoundException, EnumConversionException, IncorrectFieldException {
        int result = userService.updateUserDetails(user, authHeader);
        if (result == Const.userDoesNotExist)
            throw new UserDoesNotExist("User does not exist!");
        if (result == Const.emailAlreadyUsed)
            throw new EmailAlreadyUsedException("E-mail is already used!");
        if (result == Const.toLongEmail)
            throw new EmailToLongException("E-mail is too long!");
        if (result == Const.toLongName)
            throw new FullNameToLongException("Name is too long!");
    }

    @GetMapping
    public UserDto getUserByAuthHeader(@RequestHeader("Authorization") String authHeader)
            throws EntityNotFoundException {
        UserDto user = userService.getUserDetails(authHeader);
        return user;
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordDto passwordDto)
            throws PasswordToLongException, UserDoesNotExist, WrongPasswordException, EntityNotFoundException {
        int result = userService.updatePassword(passwordDto, authHeader);
        if (result == Const.wrongPassword)
            throw new WrongPasswordException("Wrong password!");
        if (result == Const.userDoesNotExist)
            throw new UserDoesNotExist("User does not exist!");
        if (result == Const.toLongPassword)
            throw new PasswordToLongException("Password is too long!");
    }

    @PostMapping("/authenticate")
    ResponseEntity<?> authenticateUser(@RequestBody JwtRequest authenticationRequest) {
        return userService.authenticateUser(authenticationRequest);
    }

    @GetMapping("/refreshtoken")
    ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        return userService.refreshToken(authHeader);
    }

}
