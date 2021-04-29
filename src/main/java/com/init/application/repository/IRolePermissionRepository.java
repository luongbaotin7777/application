package com.init.application.repository;

import com.init.application.entity.RolePermissionEntity;
import com.init.application.generic.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IRolePermissionRepository extends IBaseRepository<RolePermissionEntity, UUID> {
    List<RolePermissionEntity> findAllByRoleId(UUID roleId);
}
