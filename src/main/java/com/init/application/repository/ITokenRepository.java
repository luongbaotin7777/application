package com.init.application.repository;

import com.init.application.entity.TokenEntity;
import com.init.application.generic.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITokenRepository extends IBaseRepository<TokenEntity, UUID> {
    TokenEntity findByAccessToken(String accessToken);

    List<TokenEntity> findAllByRefreshToken(String refreshToken);
}
