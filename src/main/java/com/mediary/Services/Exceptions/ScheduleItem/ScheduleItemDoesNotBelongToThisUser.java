package com.mediary.Services.Exceptions.ScheduleItem;

public class ScheduleItemDoesNotBelongToThisUser extends Exception{
    public ScheduleItemDoesNotBelongToThisUser(String message){
        super(message);
    }
}