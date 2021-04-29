package com.init.application.service.role;

import com.init.application.dto.role.RoleRequestDto;
import com.init.application.dto.role.RoleResponseDto;
import com.init.application.entity.RoleEntity;
import com.init.application.generic.IBaseService;

import java.util.Map;
import java.util.UUID;

public interface IRoleService extends IBaseService<RoleEntity, UUID> {
    RoleEntity create(RoleRequestDto roleRequestDto);

    Map<String, Object> getListRole(Integer page, Integer size, String sortType, String sortBy);

    boolean existById(UUID roleId);

    RoleResponseDto findById(UUID roleId);

    RoleEntity updateRole(RoleRequestDto roleRequestDto, UUID roleId);

    RoleEntity findByRoleName(String roleName);
}
