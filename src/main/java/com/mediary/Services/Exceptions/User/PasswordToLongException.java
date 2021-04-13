package com.mediary.Services.Exceptions.User;

public class PasswordToLongException extends Exception {
    public PasswordToLongException(String message){
        super(message);
    }
}
