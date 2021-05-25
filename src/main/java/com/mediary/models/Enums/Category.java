package com.mediary.Models.Enums;

import com.mediary.Services.Exceptions.EnumConversionException;

public enum Category {
    EXAMINATION("Examination"), CONSULTATION("Consultation"), TREATMENT("Treatment");

    private String code;

    private Category(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Category convertStringToEnum(String code) throws EnumConversionException {
        if (code == null) {
            return null;
        }

        for (Category category : Category.values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }
        throw new EnumConversionException("Incorrect category code");
    }
}
