package com.init.application.service.token;

import com.init.application.dto.user.LoginReponseDto;
import com.init.application.entity.TokenEntity;
import com.init.application.generic.IBaseService;

import java.util.UUID;

public interface ITokenService extends IBaseService<TokenEntity, UUID> {
    void expiredToken(String token);

    TokenEntity findByAccessToken(String token);

    LoginReponseDto refreshToken(String refreshToken);

    TokenEntity saveToken(String accessToken, String refreshToken);

    String getCurrentToken();
}
