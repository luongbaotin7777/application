package com.init.application.dto.permission;

import com.init.application.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PermissionResponseDto extends BaseDto {
    private String permissionName;

    private String description;

    private String groupPermission;

}
