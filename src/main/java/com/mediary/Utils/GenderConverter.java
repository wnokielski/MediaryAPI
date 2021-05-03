package com.mediary.Utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.mediary.Models.Enums.Gender;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Incorrect gender name");
    }

}