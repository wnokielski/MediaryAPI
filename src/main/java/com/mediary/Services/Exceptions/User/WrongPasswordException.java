package com.mediary.Services.Exceptions.User;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message){
        super(message);
    }
}
