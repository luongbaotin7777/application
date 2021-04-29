package com.init.application.security;

import com.init.application.common.DataException;
import com.init.application.entity.UserEntity;
import com.init.application.handler_exception.RestError;
import com.init.application.handler_exception.SingleErrorException;
import com.init.application.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserDetailService implements UserDetailsService {
    private final IUserRepository userRepository;

    public UserDetailService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(userEntity);
    }

    public CustomUserDetails loadUserById(UUID userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            RestError err = RestError.builder()
                    .message(DataException.DoesNotExistUser.getValue())
                    .resource(UserEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.BAD_REQUEST);
        }

        return new CustomUserDetails(userEntity.get());
    }
}
