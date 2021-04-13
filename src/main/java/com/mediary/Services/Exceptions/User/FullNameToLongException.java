package com.mediary.Services.Exceptions.User;

public class FullNameToLongException extends Exception{
    public FullNameToLongException(String message){
        super(message);
    }
}
