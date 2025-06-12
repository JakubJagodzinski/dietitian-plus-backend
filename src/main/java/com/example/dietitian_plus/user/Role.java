package com.example.dietitian_plus.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.dietitian_plus.user.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    PATIENT(
            Set.of(
                    DISEASE_READ,
                    DISEASE_READ_ALL,

                    DISH_READ,

                    DISH_PRODUCT_READ_ALL,

                    MEAL_READ,

                    MEAL_DISH_READ_ALL,

                    PATIENT_CREATE,
                    PATIENT_READ,
                    PATIENT_UPDATE,
                    PATIENT_DELETE,

                    PATIENT_MEAL_READ_ALL,

                    PATIENT_DIETITIAN_UNASSIGN,

                    PATIENT_ALLERGENIC_PRODUCT_READ_ALL,
                    PATIENT_ALLERGENIC_PRODUCT_ASSIGN,
                    PATIENT_ALLERGENIC_PRODUCT_UNASSIGN,

                    PATIENT_DISLIKED_PRODUCT_READ_ALL,
                    PATIENT_DISLIKED_PRODUCT_ASSIGN,
                    PATIENT_DISLIKED_PRODUCT_UNASSIGN,

                    PATIENT_DISEASE_READ_ALL,
                    PATIENT_DISEASE_ASSIGN,
                    PATIENT_DISEASE_UNASSIGN,

                    PRODUCT_READ,
                    PRODUCT_READ_ALL,

                    UNIT_READ,
                    UNIT_READ_ALL
            )
    ),

    DIETITIAN(
            Set.of(
                    DIETITIAN_CREATE,
                    DIETITIAN_READ,
                    DIETITIAN_UPDATE,
                    DIETITIAN_DELETE,

                    DIETITIAN_MEAL_READ_ALL,

                    DIETITIAN_DISH_READ_ALL,

                    DIETITIAN_PATIENT_READ_ALL,

                    DIETITIAN_NOTE_READ_ALL,

                    DISEASE_READ,
                    DISEASE_READ_ALL,

                    DISH_CREATE,
                    DISH_READ,
                    DISH_UPDATE,
                    DISH_DELETE,

                    DISH_PRODUCT_ASSIGN,
                    DISH_PRODUCT_READ_ALL,
                    DISH_PRODUCT_UPDATE,
                    DISH_PRODUCT_UNASSIGN,

                    MEAL_CREATE,
                    MEAL_READ,
                    MEAL_UPDATE,
                    MEAL_DELETE,

                    MEAL_DISH_ADD,
                    MEAL_DISH_READ_ALL,
                    MEAL_DISH_REMOVE,

                    NOTE_CREATE,
                    NOTE_READ,
                    NOTE_UPDATE,
                    NOTE_DELETE,

                    PATIENT_READ,
                    PATIENT_READ_ALL,

                    PATIENT_MEAL_READ_ALL,

                    PATIENT_DIETITIAN_ASSIGN,
                    PATIENT_DIETITIAN_UNASSIGN,

                    PATIENT_ALLERGENIC_PRODUCT_READ_ALL,

                    PATIENT_DISLIKED_PRODUCT_READ_ALL,

                    PATIENT_DISEASE_READ_ALL,

                    PATIENT_NOTE_READ_ALL,

                    PRODUCT_READ,
                    PRODUCT_READ_ALL,

                    UNIT_READ,
                    UNIT_READ_ALL
            )
    ),

    ADMIN(
            Set.of(
                    Permission.values()
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
