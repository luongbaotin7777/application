package com.init.application.service.userrole;

import com.init.application.entity.UserRoleEntity;
import com.init.application.generic.BaseService;
import com.init.application.repository.IUserRoleRepository;
import com.init.application.service.role.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserRoleService extends BaseService<UserRoleEntity, UUID> implements IUserRoleService {
    private final IUserRoleRepository userRoleRepository;

    public UserRoleService(IUserRoleRepository userRoleRepository, IRoleService roleService) {
        super(userRoleRepository);
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean existsAllByUserIdAndRoleId(UUID userId, UUID roleId) {
        return userRoleRepository.existsAllByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public List<UserRoleEntity> findAllByUserId(UUID userId) {
        return userRoleRepository.findAllByUserId(userId);
    }
}
