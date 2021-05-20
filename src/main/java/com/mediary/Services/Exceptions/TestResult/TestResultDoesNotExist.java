package com.mediary.Services.Exceptions.TestResult;

public class TestResultDoesNotExist extends Exception{
    public TestResultDoesNotExist(String message){
        super(message);
    }
}