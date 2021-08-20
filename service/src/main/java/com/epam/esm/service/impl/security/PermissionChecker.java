package com.epam.esm.service.impl.security;

import com.epam.esm.model.entity.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionChecker {
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    public boolean checkUserIdPermission(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            JwtUserDetails details = (JwtUserDetails) authentication.getDetails();
            UserRole role = details.getRole();
            if (ADMIN_ROLE.equals(role.getName())) {
                return true;
            }
            long id = details.getId();
            return (userId == id);
        }
        return false;
    }

    public boolean checkAdminPermission() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            JwtUserDetails details = (JwtUserDetails) authentication.getDetails();
            UserRole role = details.getRole();
            return ADMIN_ROLE.equals(role.getName());
        }
        return false;
    }
}
