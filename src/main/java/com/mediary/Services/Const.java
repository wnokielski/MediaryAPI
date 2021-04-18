package com.mediary.Services;

public class Const {
    private Const(){}

    //Positive result
    public static final int registrationSuccess = 0;
    public static final int userDetailsUpdateSuccess =0;

    //Error codes
    public static final int emailAlreadyUsed = 1;
    public static final int usernameAlreadyUsed = 2;
    public static final int toLongUsername= 3;
    public static final int toLongEmail= 4;
    public static final int toLongPassword= 5;
    public static final int toLongName= 6;
    public static final int userDoesNotExist= 2;
    public static final int wrongPassword= 7;

    //Authentication error codes
    public static final int badCredentials = 8;
}
