package com.init.application.repository;

import com.init.application.entity.RoleEntity;
import com.init.application.generic.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRoleRepository extends IBaseRepository<RoleEntity, UUID> {
    boolean existsById(UUID roleId);

    boolean existsByRoleName(String roleName);

    RoleEntity findByRoleName(String roleName);
}
