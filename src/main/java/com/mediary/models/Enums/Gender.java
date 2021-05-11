package com.mediary.Models.Enums;

import com.mediary.Services.Exceptions.EnumConversionException;

public enum Gender {
    MALE("Male"), FEMALE("Female"), UNDEFINED("Undefined");

    private String code;

    private Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Gender convertStringToEnum(String code) throws EnumConversionException {
        if (code == null) {
            return null;
        }

        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new EnumConversionException("Incorrect gender code");
    }
}
