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
                    PATIENT_READ,
                    PATIENT_UPDATE,
                    PATIENT_DELETE,
                    PATIENT_CREATE
            )
    ),

    DIETITIAN(
            Set.of(
                    DIETITIAN_READ,
                    DIETITIAN_UPDATE,
                    DIETITIAN_DELETE,
                    DIETITIAN_CREATE
            )
    ),

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
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
