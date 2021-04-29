package com.init.application.service.rolepermission;

import com.init.application.entity.RolePermissionEntity;
import com.init.application.generic.IBaseService;

import java.util.List;
import java.util.UUID;

public interface IRolePermissionService extends IBaseService<RolePermissionEntity, UUID> {
    List<RolePermissionEntity> findAllByRoleId(UUID roleId);
}
