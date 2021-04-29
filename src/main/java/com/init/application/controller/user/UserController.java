package com.init.application.controller.user;

import com.init.application.common.PermissionUtils;
import com.init.application.dto.user.*;
import com.init.application.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class UserController implements IUserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    @PreAuthorize(PermissionUtils.CREATE_USER)
    public ResponseEntity createUserByAdmin(AdminCreateDto adminCreateDto) {
        return new ResponseEntity<>(userService.createUserByAdmin(adminCreateDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity registerUser(UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(PermissionUtils.VIEW_USER)
    public ResponseEntity getListUser(Integer page, Integer size, String sortType, String sortBy) {
        return new ResponseEntity<>(userService.getListUser(page, size, sortType, sortBy), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(PermissionUtils.VIEW_USER)
    public ResponseEntity getDetailUser(UUID userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateUser(UUID userId, UserUpdateDto userUpdateDto) {
        return new ResponseEntity<>((userService.updateUser(userId, userUpdateDto)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(PermissionUtils.UPDATE_USER)
    public ResponseEntity updateUserByAdmin(UUID userId, UpdateUserByAdminDto updateUserByAdminDto) {
        return new ResponseEntity<>((userService.updateUserByAdmin(userId, updateUserByAdminDto)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity login(LoginRequestDto loginDto) {
        return new ResponseEntity<>((userService.login(loginDto)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity refreshToken(String refreshToken) {
        return new ResponseEntity<>((userService.refreshToken(refreshToken)), HttpStatus.OK);
    }
}
