package com.init.application.dto.role;

import com.init.application.dto.base.BaseDto;
import com.init.application.dto.permission.PermissionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleResponseDto extends BaseDto {
    private String roleName;

    private String description;

    List<PermissionResponseDto> permissionEntities;

}
