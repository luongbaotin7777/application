package com.init.application.controller.role;

import com.init.application.common.PermissionUtils;
import com.init.application.dto.role.RoleRequestDto;
import com.init.application.service.role.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RoleController implements IRoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
//    @PreAuthorize(PermissionUtils.CREATE_ROLE)
    public ResponseEntity create(RoleRequestDto roleRequestDto) {
        return new ResponseEntity(roleService.create(roleRequestDto), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize(PermissionUtils.VIEW_ROLE)
    public ResponseEntity getListRole(Integer page, Integer size, String sortType, String sortBy) {
        return new ResponseEntity((roleService.getListRole(page, size, sortType, sortBy)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize(PermissionUtils.VIEW_ROLE)
    public ResponseEntity getDetailRole(UUID roleId) {
        return new ResponseEntity(roleService.findById(roleId), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize(PermissionUtils.UPDATE_ROLE)
    public ResponseEntity updateRole(RoleRequestDto roleRequestDto, UUID roleId) {
        return new ResponseEntity(roleService.updateRole(roleRequestDto, roleId), HttpStatus.OK);
    }
}
