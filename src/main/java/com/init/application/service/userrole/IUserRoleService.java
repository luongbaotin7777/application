package com.init.application.service.userrole;

import com.init.application.entity.UserRoleEntity;
import com.init.application.generic.IBaseService;

import java.util.List;
import java.util.UUID;

public interface IUserRoleService extends IBaseService<UserRoleEntity, UUID> {
    boolean existsAllByUserIdAndRoleId(UUID userId, UUID roleId);

    List<UserRoleEntity> findAllByUserId(UUID userId);
}
