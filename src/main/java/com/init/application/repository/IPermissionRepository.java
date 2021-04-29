package com.init.application.repository;

import com.init.application.entity.PermissionEntity;
import com.init.application.generic.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IPermissionRepository extends IBaseRepository<PermissionEntity, UUID> {
    boolean existsByPermissionName(String permissionName);

    boolean existsById(UUID permissionId);

    PermissionEntity findByPermissionName(String permissionName);
}
