package com.example.dietitian_plus.auth.access.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
public @interface AdminOnly {
}
