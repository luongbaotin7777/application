package com.init.application.service.permission;

import com.init.application.dto.permission.PermissionRequestDto;
import com.init.application.dto.permission.PermissionResponseDto;
import com.init.application.entity.PermissionEntity;
import com.init.application.generic.IBaseService;

import java.util.Map;
import java.util.UUID;

public interface IPermissionService extends IBaseService<PermissionEntity, UUID> {
    PermissionEntity create(PermissionRequestDto permissionRequestDto);

    Map<String, Object> getListPermission(Integer page, Integer size, String sortType, String sortBy);

    boolean existById(UUID permissionId);

    PermissionResponseDto findById(UUID permissionId);

    PermissionEntity updatePermission(UUID permissionId, PermissionRequestDto permissionRequestDto);

    PermissionEntity findByPermissionName(String permissionName);

}
