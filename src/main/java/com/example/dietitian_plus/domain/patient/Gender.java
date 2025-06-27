package com.example.dietitian_plus.domain.patient;

public enum Gender {

    MALE,
    FEMALE,
    OTHER;

    public static boolean isValidGender(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
