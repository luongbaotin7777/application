package com.init.application.mapper;

import com.init.application.dto.user.AdminCreateDto;
import com.init.application.dto.user.UserRequestDto;
import com.init.application.dto.user.UserResponseDto;
import com.init.application.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserEntity toUserEntity(UserRequestDto userRequestDto);

    UserEntity toUserEntityByAdmin(AdminCreateDto adminCreateDto);

    UserResponseDto toUserResponseDto(UserEntity userEntity);

    List<UserEntity> toUserEntities(List<UserRequestDto> userRequestDtos);

    List<UserResponseDto> toUserResponseDtos(List<UserEntity> userEntities);
}
