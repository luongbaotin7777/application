package com.init.application.mapper;

import com.init.application.dto.role.RoleRequestDto;
import com.init.application.dto.role.RoleResponseDto;
import com.init.application.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    RoleEntity toRoleEntity(RoleRequestDto roleRequestDto);

    RoleResponseDto toRoleResponseDto(RoleEntity roleEntity);

    List<RoleEntity> toRoleEntities(List<RoleRequestDto> roleRequestDtos);

    List<RoleResponseDto> toRoleResponseDtos(List<RoleEntity> roleEntities);
}
