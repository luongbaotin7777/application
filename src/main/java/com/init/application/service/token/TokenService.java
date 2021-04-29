package com.init.application.service.token;

import com.init.application.common.CommonUtils;
import com.init.application.common.DataException;
import com.init.application.dto.user.LoginReponseDto;
import com.init.application.entity.TokenEntity;
import com.init.application.generic.BaseService;
import com.init.application.handler_exception.RestError;
import com.init.application.handler_exception.SingleErrorException;
import com.init.application.repository.ITokenRepository;
import com.init.application.security.CustomUserDetails;
import com.init.application.security.TokenProvider;
import com.init.application.security.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TokenService extends BaseService<TokenEntity, UUID> implements ITokenService {
    @Value(value = "${jwt.refreshExpired}")
    public long REFRESH_EXPIRED;

    private final ITokenRepository repository;

    private final TokenProvider tokenProvider;
    private final UserDetailService userDetailService;

    public TokenService(ITokenRepository repository, TokenProvider tokenProvider, UserDetailService userDetailService) {
        super(repository);
        this.repository = repository;
        this.tokenProvider = tokenProvider;
        this.userDetailService = userDetailService;
    }

    @Override
    public void expiredToken(String token) {
        TokenEntity tokenEntity = this.findByAccessToken(token);
        tokenEntity.setExpiredAccess(CommonUtils.NOW);

        repository.save(tokenEntity);
    }

    @Override
    public TokenEntity findByAccessToken(String token) {
        TokenEntity tokenEntity = repository.findByAccessToken(token);
        if (tokenEntity == null) {
            RestError restError = RestError.builder()
                    .message(DataException.DoesNotExistToken.getValue())
                    .resource(TokenEntity.class.toString())
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            throw new SingleErrorException(restError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return tokenEntity;
    }

    @Override
    public LoginReponseDto refreshToken(String refreshToken) {
        List<TokenEntity> tokenEntities = repository.findAllByRefreshToken(refreshToken);
        if (tokenEntities == null) {
            RestError error = RestError.builder()
                    .message(DataException.DoesNotExistRefreshToken.getValue())
                    .resource(TokenEntity.class.toString())
                    .build();
            throw new SingleErrorException(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tokenEntities.stream().anyMatch(tokenEntity -> tokenEntity.getExpiredRefresh().before(new Date()))) {
            RestError error = RestError.builder()
                    .message(DataException.RefeshTokenExpired.getValue())
                    .resource(TokenEntity.class.toString())
                    .build();
            throw new SingleErrorException(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String currentAccessToken = tokenEntities.stream().map(TokenEntity::getAccessToken)
                .findFirst().orElseThrow(() -> new SingleErrorException(RestError.builder()
                        .message("AccessToken is empty")
                        .resource(TokenEntity.class.toString())
                        .build(), HttpStatus.BAD_REQUEST));

        String userId = tokenProvider.getUserIdFromToken(currentAccessToken);
        CustomUserDetails customUserDetails = userDetailService.loadUserById(UUID.fromString(userId));
        String newAccessToken = tokenProvider.generateToken(customUserDetails);

        this.expiredToken(currentAccessToken);
        this.saveToken(newAccessToken, refreshToken);

        return LoginReponseDto.builder().accessToken(newAccessToken).refreshToken(refreshToken).build();
    }

    @Override
    public TokenEntity saveToken(String accessToken, String refreshToken) {
        TokenEntity saveToken = TokenEntity.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAccess(tokenProvider.getExpirationDateFromToken(accessToken))
                .expiredRefresh(new Date(System.currentTimeMillis() + REFRESH_EXPIRED * 1000))
                .build();

        return repository.save(saveToken);
    }

    @Override
    public String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getCredentials().toString();
        }
        return null;
    }
}
