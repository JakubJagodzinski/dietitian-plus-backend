package com.example.dietitian_plus.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    DIETITIAN_READ("dietitian:read"),
    DIETITIAN_READ_ALL("dietitian:read_all"),
    DIETITIAN_UPDATE("dietitian:update"),
    DIETITIAN_DELETE("dietitian:delete"),

    DIETITIAN_MEAL_READ_ALL("dietitian:meal:read:all"),

    DIETITIAN_DISH_READ_ALL("dietitian:dish:read:all"),

    DIETITIAN_PATIENT_READ_ALL("dietitian:patient:read_all"),

    DIETITIAN_NOTE_READ_ALL("dietitian:note:read_all"),

    DISEASE_CREATE("disease:create"),
    DISEASE_READ("disease:read"),
    DISEASE_READ_ALL("disease:read_all"),
    DISEASE_UPDATE("disease:update"),
    DISEASE_DELETE("disease:delete"),

    DISH_CREATE("dish:create"),
    DISH_READ("dish:read"),
    DISH_READ_ALL("dish:read_all"),
    DISH_UPDATE("dish:update"),
    DISH_DELETE("dish:delete"),

    DISH_PRODUCT_ASSIGN("dish:product:assign"),
    DISH_PRODUCT_READ_ALL("dish:product:read:all"),
    DISH_PRODUCT_UPDATE("dish:product:update"),
    DISH_PRODUCT_UNASSIGN("dish:product:unassign"),

    MEAL_CREATE("meal:create"),
    MEAL_READ("meal:read"),
    MEAL_READ_ALL("meal:read_all"),
    MEAL_UPDATE("meal:update"),
    MEAL_DELETE("meal:delete"),

    MEAL_DISH_ADD("meal:dish:add"),
    MEAL_DISH_READ_ALL("meal:dish:read:all"),
    MEAL_DISH_REMOVE("meal:dish:remove"),

    NOTE_CREATE("note:create"),
    NOTE_READ("note:read"),
    NOTE_READ_ALL("note:read_all"),
    NOTE_UPDATE("note:update"),
    NOTE_DELETE("note:delete"),

    PATIENT_READ("patient:read"),
    PATIENT_READ_ALL("patient:read_all"),
    PATIENT_UPDATE("patient:update"),
    PATIENT_DELETE("patient:delete"),

    PATIENT_MEAL_READ_ALL("patient:meal:read:all"),

    PATIENT_DIETITIAN_ASSIGN("patient:dietitian:assign"),
    PATIENT_DIETITIAN_UNASSIGN("patient:dietitian:unassign"),

    PATIENT_ALLERGENIC_PRODUCT_READ_ALL("patient:allergenic_product:read:all"),
    PATIENT_ALLERGENIC_PRODUCT_ASSIGN("patient:allergenic_product:assign"),
    PATIENT_ALLERGENIC_PRODUCT_UNASSIGN("patient:allergenic_product:unassign"),

    PATIENT_DISLIKED_PRODUCT_READ_ALL("patient:disliked_product:read:all"),
    PATIENT_DISLIKED_PRODUCT_ASSIGN("patient:disliked_product:assign"),
    PATIENT_DISLIKED_PRODUCT_UNASSIGN("patient:disliked_product:unassign"),

    PATIENT_DISEASE_READ_ALL("patient:disease:read:all"),
    PATIENT_DISEASE_ASSIGN("patient:disease:assign"),
    PATIENT_DISEASE_UNASSIGN("patient:disease:unassign"),

    PATIENT_NOTE_READ_ALL("patient:note:read_all"),

    PRODUCT_CREATE("product:create"),
    PRODUCT_READ("product:read"),
    PRODUCT_READ_ALL("product:read_all"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    UNIT_CREATE("unit:create"),
    UNIT_READ("unit:read"),
    UNIT_READ_ALL("unit:read_all"),
    UNIT_UPDATE("unit:update"),
    UNIT_DELETE("unit:delete");

    private final String permission;

}
