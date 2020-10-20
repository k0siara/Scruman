package com.scruman.backend.beans;

import com.scruman.backend.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

@FunctionalInterface
public interface CurrentUser {
    @PreAuthorize("isAuthenticated()")
    User get();
}


