package com.mediary.Services.Exceptions.User;

public class UserDoesNotExist extends Exception{
    public UserDoesNotExist(String message){
        super(message);
    }
}
