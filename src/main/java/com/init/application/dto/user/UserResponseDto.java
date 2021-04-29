package com.init.application.dto.user;

import com.init.application.dto.base.BaseDto;
import com.init.application.dto.role.RoleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponseDto extends BaseDto {
    private String userName;

    private String email;

    private List<RoleResponseDto> roleEntities;
}
