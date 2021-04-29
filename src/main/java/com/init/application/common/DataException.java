package com.init.application.common;

public enum DataException {
    // ------------------------
    // Role
    // ------------------------
    RoleIsExistingInUser("Role is existing in user"),
    DoesNotExistRole("Does not exist role"),
    RoleAlreadyExist("Role already exist"),

    // ------------------------
    // User
    // ------------------------
    UserAlreadyExist("User already exist"),
    EmailAlreadyExist("Email already exist"),
    DoesNotExistUser("Doest not exist user"),
    WrongCurrentPassword("Wrong current password"),
    NewPasswordIsSameCurrentPassword("New password is same current password"),

    // ------------------------
    // Permission
    // ------------------------
    PermissionAlreadyExist("User already exist"),

    DoesNotExistPermission("Does not exist permission"),
    // ------------------------
    // Token
    // ------------------------
    DoesNotExistToken("Does not exist token"),
    RefeshTokenExpired("Refresh token expired"),
    CurrentTokenDoestNotBeLongRefreshToken("Current token doest not belong refresh token"),
    DoesNotExistRefreshToken("Does not exist refresh token");

    private final String value;

    DataException(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
