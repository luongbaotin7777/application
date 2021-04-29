package com.init.application.service.user;

import com.init.application.common.CommonUtils;
import com.init.application.common.DataException;
import com.init.application.dto.user.*;
import com.init.application.entity.RoleEntity;
import com.init.application.entity.UserEntity;
import com.init.application.entity.UserRoleEntity;
import com.init.application.filter.JwtAuthenticationFilter;
import com.init.application.generic.BaseService;
import com.init.application.handler_exception.RestError;
import com.init.application.handler_exception.SingleErrorException;
import com.init.application.mapper.IUserMapper;
import com.init.application.repository.IUserRepository;
import com.init.application.security.CustomUserDetails;
import com.init.application.security.TokenProvider;
import com.init.application.security.UserDetailService;
import com.init.application.service.role.IRoleService;
import com.init.application.service.token.ITokenService;
import com.init.application.service.userrole.IUserRoleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserService extends BaseService<UserEntity, UUID> implements IUserService {
    @Value(value = "${jwt.refreshExpired}")
    public long REFRESH_EXPIRED;

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    private final IUserRoleService userRoleService;
    private final IRoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final TokenProvider tokenProvider;
    private final ITokenService tokenService;

    public UserService(IUserRepository userRepository, IUserMapper userMapper, IUserRoleService userRoleService,
                       IRoleService roleService, AuthenticationManager authenticationManager,
                       UserDetailService userDetails, TokenProvider tokenProvider, ITokenService tokenService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetails;
        this.tokenProvider = tokenProvider;
        this.tokenService = tokenService;
    }

    @Override
    public UserEntity createUserByAdmin(AdminCreateDto adminCreateDto) {
        UserEntity userEntity = userMapper.toUserEntityByAdmin(adminCreateDto);
        saveUser(userEntity, adminCreateDto.getPassword());

        return addRoles(userEntity, adminCreateDto.getRoleIds());
    }

    private UserEntity addRoles(UserEntity userEntity, List<UUID> roleIds) {
        List<UserRoleEntity> userRoleEntities = userRoleService.findAllByUserId(userEntity.getId());
        if (!userRoleEntities.isEmpty()) {
            userRoleService.deleteInBatch(userRoleEntities);
        }

        for (UUID roleId : roleIds) {
            if (!roleService.existById(roleId)) {
                RestError err = RestError.builder()
                        .message(DataException.DoesNotExistRole.getValue())
                        .resource(UserRoleEntity.class.toString())
                        .build();
                throw new SingleErrorException(err, HttpStatus.BAD_REQUEST);
            }
            UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                    .userId(userEntity.getId())
                    .roleId(roleId)
                    .build();

            userRoleService.create(userRoleEntity);
        }

        return userEntity;
    }

    @Override
    public UserEntity registerUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = userMapper.toUserEntity(userRequestDto);
        saveUser(userEntity, userRequestDto.getPassword());

        RoleEntity roleEntity = roleService.findByRoleName(CommonUtils.ROLE_USER);
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .userId(userEntity.getId())
                .roleId(roleEntity.getId())
                .build();
        userRoleService.create(userRoleEntity);

        return userEntity;
    }

    private void saveUser(UserEntity userEntity, String password) {
        if (userRepository.existsByUserName(userEntity.getUserName())) {
            RestError err = RestError.builder()
                    .message(DataException.UserAlreadyExist.getValue())
                    .resource(UserRoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(userEntity.getEmail())) {
            RestError err = RestError.builder()
                    .message(DataException.EmailAlreadyExist.getValue())
                    .resource(UserRoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.save(userEntity);
    }


    @Override
    public Map<String, Object> getListUser(Integer page, Integer size, String sortType, String sortBy) {
        Map<String, Object> results = this.findAllPage(page, size, sortType, sortBy);

        List<UserEntity> userEntities = (List<UserEntity>) results.get(CommonUtils.resultContent);
        List<UserResponseDto> userResponseDtos = userMapper.toUserResponseDtos(userEntities);

        results.remove(CommonUtils.resultContent);
        results.put("listUsers", userResponseDtos);

        return results;
    }

    @Override
    public UserResponseDto findById(UUID userId) {
        return userMapper.toUserResponseDto(userRepository.findById(userId).orElseThrow(()
                -> new SingleErrorException(RestError.builder()
                .message(DataException.UserAlreadyExist.getValue())
                .resource(UserRoleEntity.class.toString())
                .build(), HttpStatus.BAD_REQUEST)));
    }

    @Override
    public UserEntity updateUser(UUID userId, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = updateInforUser(userId, userUpdateDto.getEmail(), userUpdateDto.getCurrentPassword(),
                userUpdateDto.getNewPassword());

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUserByAdmin(UUID userId, UpdateUserByAdminDto updateUserByAdminDto) {
        UserEntity userEntity = updateInforUser(userId, updateUserByAdminDto.getEmail(), updateUserByAdminDto.getCurrentPassword(),
                updateUserByAdminDto.getNewPassword());

        return addRoles(userEntity, updateUserByAdminDto.getRoleIds());
    }

    @Override
    public LoginReponseDto login(LoginRequestDto loginDto) {
        authenticate(loginDto.getUserName(), loginDto.getPassword());

        CustomUserDetails customUserDetails = userDetailService
                .loadUserByUsername(loginDto.getUserName());

        String token = tokenProvider.generateToken(customUserDetails);
        String refreshToken = tokenProvider.refreshToken();

        tokenService.saveToken(token, refreshToken);

        return LoginReponseDto.builder().refreshToken(refreshToken).accessToken(token).build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        String currentToken = JwtAuthenticationFilter.getJwtFromRequest(request);
        tokenService.expiredToken(currentToken);
    }

    @Override
    public LoginReponseDto refreshToken(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }

    @SneakyThrows
    private void authenticate(String userName, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            throw new SingleErrorException(RestError.builder()
                    .message("USER_DISABLED")
                    .resource(UserEntity.class.toString())
                    .status(HttpStatus.BAD_REQUEST)
                    .build(), HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new SingleErrorException(RestError.builder()
                    .message("Username or password invalid")
                    .resource(UserEntity.class.toString())
                    .status(HttpStatus.BAD_REQUEST)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    private UserEntity updateInforUser(UUID userId, String email, String currentPassword, String newPassword) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()
                -> new SingleErrorException(RestError.builder()
                .message(DataException.DoesNotExistUser.getValue())
                .resource(UserRoleEntity.class.toString())
                .build(), HttpStatus.BAD_REQUEST));

        checkEmailAlreadyExist(email, userEntity);
        checkCurrentPassword(currentPassword, newPassword, userEntity);

        userEntity.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userEntity.setEmail(email);

        return userEntity;
    }

    private void checkCurrentPassword(String currenpassword, String newPassword, UserEntity userEntity) {
        String currentPassword = userEntity.getPassword();
        if (!new BCryptPasswordEncoder().matches(currenpassword, currentPassword)) {
            RestError err = RestError.builder()
                    .message(DataException.WrongCurrentPassword.getValue())
                    .resource(RoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.NOT_FOUND);
        }
        if (new BCryptPasswordEncoder().matches(newPassword, currentPassword)) {
            RestError err = RestError.builder()
                    .message(DataException.NewPasswordIsSameCurrentPassword.getValue())
                    .resource(RoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.NOT_FOUND);
        }
    }

    private void checkEmailAlreadyExist(String email, UserEntity userEntity) {
        if (userRepository.existsByEmail(email)
                && !userEntity.getEmail().equals(email)) {
            RestError err = RestError.builder()
                    .message(DataException.EmailAlreadyExist.getValue())
                    .resource(RoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.NOT_FOUND);
        }
    }
}
