package com.mediary.Services.Exceptions.User;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException(String message){
        super(message);
    }
}
