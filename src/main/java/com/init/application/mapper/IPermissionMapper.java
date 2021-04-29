package com.init.application.mapper;

import com.init.application.dto.permission.PermissionRequestDto;
import com.init.application.dto.permission.PermissionResponseDto;
import com.init.application.entity.PermissionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    PermissionEntity toPermissionEntity(PermissionRequestDto permissionRequestDto);

    PermissionResponseDto toPermissionResponseDto(PermissionEntity permissionEntity);

    List<PermissionEntity> toPermissionEntities(List<PermissionRequestDto> permissionRequestDtos);

    List<PermissionResponseDto> toPermissionResponseDtos(List<PermissionEntity> permissionEntities);
}
