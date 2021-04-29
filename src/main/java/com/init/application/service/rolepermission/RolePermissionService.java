package com.init.application.service.rolepermission;

import com.init.application.entity.RolePermissionEntity;
import com.init.application.generic.BaseService;
import com.init.application.generic.IBaseRepository;
import com.init.application.repository.IRolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RolePermissionService extends BaseService<RolePermissionEntity, UUID> implements IRolePermissionService {
    private final IRolePermissionRepository rolePermissionRepository;

    public RolePermissionService(IBaseRepository<RolePermissionEntity, UUID> repository,
                                 IRolePermissionRepository rolePermissionRepository) {
        super(repository);
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public List<RolePermissionEntity> findAllByRoleId(UUID roleId) {
        return rolePermissionRepository.findAllByRoleId(roleId);
    }
}
