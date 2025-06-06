package com.example.dietitian_plus.utils;

public class FloatParser {

    public static Float parse(String val) {
        try {
            return Float.parseFloat(val.trim());
        } catch (Exception e) {
            return 0.0f;
        }
    }

}
