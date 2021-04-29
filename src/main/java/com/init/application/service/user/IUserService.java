package com.init.application.service.user;

import com.init.application.dto.user.*;
import com.init.application.entity.UserEntity;
import com.init.application.generic.IBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

public interface IUserService extends IBaseService<UserEntity, UUID> {
    UserEntity createUserByAdmin(AdminCreateDto adminCreateDto);

    UserEntity registerUser(UserRequestDto userRequestDto);

    Map<String, Object> getListUser(Integer page, Integer size, String sortType, String sortBy);

    UserResponseDto findById(UUID userId);

    UserEntity updateUser(UUID userId, UserUpdateDto userUpdateDto);

    UserEntity updateUserByAdmin(UUID userId, UpdateUserByAdminDto updateUserByAdminDto);

    LoginReponseDto login(LoginRequestDto loginDto);

    void logout(HttpServletRequest request);

    LoginReponseDto refreshToken(String refreshToken);
}
