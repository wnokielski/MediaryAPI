package com.mediary.Services.Exceptions.User;

public class EmailToLongException extends Exception{
    public EmailToLongException(String message){
        super(message);
    }
}
