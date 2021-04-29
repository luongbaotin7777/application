package com.init.application.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object accessType, Object permission) {
        if (authentication != null && accessType instanceof String) {
            if ("permission".equalsIgnoreCase(String.valueOf(accessType))) {

                Object principal = authentication.getPrincipal();
                if (!(principal instanceof String)) {
                    List<String> permissionEntities = new ArrayList<>();
                    ((CustomUserDetails) principal).userEntity.getRoleEntities().forEach(roleEntity -> roleEntity.getPermissionEntities()
                            .forEach(permissionEntity -> permissionEntities.add(permissionEntity.getPermissionName())));

                    return validateAccess(permissionEntities, String.valueOf(permission));
                }
            }
            return false;
        }
        return false;
    }

    private boolean validateAccess(List<String> permissionEntities, String permission) {
        List<String> listPermission = new ArrayList<>();
        for (String permission1 : permissionEntities) {
            if (permission1.equalsIgnoreCase(permission)) {
                listPermission.add(permission1);
            }
        }
        // ideally should be checked with user role, permission in database
        return !listPermission.isEmpty();

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String targetType,
                                 Object permission) {
        return false;
    }
}
