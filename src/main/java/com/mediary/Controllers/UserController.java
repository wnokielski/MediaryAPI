package com.mediary.Controllers;

import com.mediary.Models.Entities.UserEntity;
import com.mediary.Services.Const;
import com.mediary.Services.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.mediary.Services.Exceptions.User.EmailAlreadyUsedException;
import com.mediary.Services.Exceptions.User.EmailToLongException;
import com.mediary.Services.Exceptions.User.UsernameToLongException;
import com.mediary.Services.Exceptions.User.UsernameAlreadyUsedException;
import com.mediary.Services.Exceptions.User.FullNameToLongException;
import com.mediary.Services.Exceptions.User.PasswordToLongException;

@CrossOrigin
@RestController
@RequestMapping("/api")
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
}
