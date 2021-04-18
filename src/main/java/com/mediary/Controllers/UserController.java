package com.mediary.Controllers;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Services.Const;
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
    public void registerNewUser(@RequestBody UserRegisterDto user)
            throws UsernameToLongException, UsernameAlreadyUsedException, EmailToLongException, PasswordToLongException,
            EmailAlreadyUsedException, FullNameToLongException {
        int result = userService.registerNewUser(user);
        if (result == Const.emailAlreadyUsed)
            throw new EmailAlreadyUsedException("E-mail is already used!");
        if (result == Const.usernameAlreadyUsed)
            throw new UsernameAlreadyUsedException("Username is already used!");
        if (result == Const.toLongEmail)
            throw new EmailToLongException("E-mail is too long!");
        if (result == Const.toLongUsername)
            throw new UsernameToLongException("Username is too long!");
        if (result == Const.toLongName)
            throw new FullNameToLongException("Typed name is too long!");
        if (result == Const.toLongPassword)
            throw new PasswordToLongException("Typed password is too long!");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserDetails(@PathVariable int id, @RequestBody UserUpdateDto user)
            throws EmailAlreadyUsedException, FullNameToLongException, EmailToLongException, UserDoesNotExist {
        int result = userService.updateUserDetails(user, id);
        if (result == Const.userDoesNotExist)
            throw new UserDoesNotExist("User does not exist!");
        if (result == Const.emailAlreadyUsed)
            throw new EmailAlreadyUsedException("E-mail is already used!");
        if (result == Const.toLongEmail)
            throw new EmailToLongException("E-mail is too long!");
        if (result == Const.toLongName)
            throw new FullNameToLongException("Name is too long!");
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) throws UserNotFoundException {
        UserDto user = userService.getUserById(id);
        if (user == null)
            throw new UserNotFoundException("There is no such user");
        return user;
    }

    @PutMapping("/password/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable int id, @RequestBody ChangePasswordDto passwordDto)
            throws PasswordToLongException, UserDoesNotExist, WrongPasswordException {
        int result = userService.updatePassword(passwordDto, id);
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

}
