package com.mediary.Controllers;

import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.User.*;
import com.mediary.Services.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerNewUser(@RequestBody UserEntity user) throws UsernameToLongException, UsernameAlreadyUsedException, EmailToLongException, PasswordToLongException, EmailAlreadyUsedException, FullNameToLongException{
        int result = userService.registerNewUser(user);
        if(result == Const.emailAlreadyUsed) throw new EmailAlreadyUsedException("E-mail is already used!");
        if(result == Const.usernameAlreadyUsed) throw new UsernameAlreadyUsedException("Username is already used!");
        if(result == Const.toLongEmail) throw new EmailToLongException("E-mail is too long!");
        if(result == Const.toLongUsername) throw new UsernameToLongException("Username is too long!");
        if(result == Const.toLongName) throw new FullNameToLongException("Typed name is too long!");
        if(result == Const.toLongPassword) throw new PasswordToLongException("Typed password is too long!");
    }
    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserDetails(@PathVariable int id, @RequestBody UserEntity user) throws EmailAlreadyUsedException, FullNameToLongException, EmailToLongException, UserDoesNotExist {
        user.setId(id);
        int result = userService.updateUserDetails(user);
        if(result == Const.userDoesNotExist)
            throw new UserDoesNotExist("User does not exist!");
        if(result == Const.emailAlreadyUsed)
            throw new EmailAlreadyUsedException("E-mail is already used!");
        if(result == Const.toLongEmail)
            throw new EmailToLongException("E-mail is too long!");
        if(result == Const.toLongName)
            throw new FullNameToLongException("Name is too long!");
    }
    @GetMapping("/user/{id}")
    public Optional<UserEntity> getUserById(@PathVariable int id) throws UserNotFoundException{
        Optional<UserEntity> user = userService.getUserById(id);
        if(user == null) throw new UserNotFoundException("There is no such user");
        return  user;
    }
    @PutMapping("user/password/{id}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable int id, @PathVariable String password) throws PasswordToLongException, UserDoesNotExist {
        int result = userService.updatePassword(password, id);
        if(result == Const.userDoesNotExist)
            throw new UserDoesNotExist("User does not exist!");
        if(result == Const.toLongPassword)
            throw new PasswordToLongException("Password is too long!");
    }
}
