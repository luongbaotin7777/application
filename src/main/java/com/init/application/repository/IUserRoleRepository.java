package com.init.application.repository;

import com.init.application.entity.UserEntity;
import com.init.application.entity.UserRoleEntity;
import com.init.application.generic.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserRoleRepository extends IBaseRepository<UserRoleEntity, UUID> {
    boolean existsAllByUserIdAndRoleId(UUID userId, UUID roleId);

    List<UserRoleEntity> findAllByUserId(UUID userId);
}
