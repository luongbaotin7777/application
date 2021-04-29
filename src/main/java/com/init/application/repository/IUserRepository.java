package com.init.application.repository;

import com.init.application.entity.UserEntity;
import com.init.application.generic.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends IBaseRepository<UserEntity, UUID> {
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    UserEntity findByUserName(String username);
}
