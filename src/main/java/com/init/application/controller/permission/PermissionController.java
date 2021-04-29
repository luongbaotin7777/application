package com.init.application.controller.permission;

import com.init.application.common.PermissionUtils;
import com.init.application.dto.permission.PermissionRequestDto;
import com.init.application.service.permission.IPermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PermissionController implements IPermissionController {
    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    @PreAuthorize(PermissionUtils.CREATE_PERMISSION)
    public ResponseEntity create(PermissionRequestDto permissionRequestDto) {
        return new ResponseEntity(permissionService.create(permissionRequestDto), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(PermissionUtils.VIEW_PERMISSION)
    public ResponseEntity getListPermission(Integer page, Integer size, String sortType, String sortBy) {
        return new ResponseEntity((permissionService.getListPermission(page, size, sortType, sortBy)), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(PermissionUtils.VIEW_PERMISSION)
    public ResponseEntity getDetailPermission(UUID permissionId) {
        return new ResponseEntity(permissionService.findById(permissionId), HttpStatus.OK);
    }

    @Override
    @PreAuthorize(PermissionUtils.UPDATE_PERMISSION)
    public ResponseEntity updatePermission(UUID permissionId, PermissionRequestDto permissionRequestDto) {
        return new ResponseEntity(permissionService.updatePermission(permissionId, permissionRequestDto), HttpStatus.OK);
    }
}
