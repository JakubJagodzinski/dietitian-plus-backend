package com.example.dietitian_plus.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    PATIENT_READ("patient:read"),
    PATIENT_UPDATE("patient:update"),
    PATIENT_CREATE("patient:create"),
    PATIENT_DELETE("patient:delete"),

    DIETITIAN_READ("dietitian:read"),
    DIETITIAN_UPDATE("dietitian:update"),
    DIETITIAN_CREATE("dietitian:create"),
    DIETITIAN_DELETE("dietitian:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");

    private final String permission;

}
