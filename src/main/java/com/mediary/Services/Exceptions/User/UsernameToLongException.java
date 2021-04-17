package com.mediary.Services.Exceptions.User;

public class UsernameToLongException extends Exception {
    public UsernameToLongException(String message){
        super(message);
    }
}
