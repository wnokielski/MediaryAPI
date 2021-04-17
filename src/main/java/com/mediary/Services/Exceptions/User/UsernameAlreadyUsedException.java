package com.mediary.Services.Exceptions.User;

public class UsernameAlreadyUsedException extends Exception {
    public UsernameAlreadyUsedException(String message){
        super(message);
    }
}
