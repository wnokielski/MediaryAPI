package com.mediary.Services.Exceptions;

public class EntityDoesNotBelongToUser extends Exception {
    public EntityDoesNotBelongToUser(String message) {
        super(message);
    }
}