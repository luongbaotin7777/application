package com.init.application.controller.role;

import com.init.application.common.ApiUtils;
import com.init.application.dto.role.RoleRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Role")
@RequestMapping(value = ApiUtils.Role, produces = MediaType.APPLICATION_JSON_VALUE)
public interface IRoleController {
    // ---------------
    // Create new role
    // ---------------
    @ApiOperation(value = "API to create new role")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Post Successfully")})
    @PostMapping
    ResponseEntity create(@RequestBody RoleRequestDto roleRequestDto);

    // ---------------
    // Get list role
    // ---------------
    @ApiOperation(value = "API to get list role")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Successfully")})
    @GetMapping
    ResponseEntity getListRole(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                               @RequestParam(name = "sortType", required = false, defaultValue = "ASC") String sortType,
                               @RequestParam(name = "sortBy", required = false, defaultValue = "createdTime") String sortBy);

    // ---------------
    // Get detail role
    // ---------------
    @ApiOperation(value = "API to get detail role")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Successfully")})
    @GetMapping(ApiUtils.roleId)
    ResponseEntity getDetailRole(@PathVariable(value = "roleId") UUID roleId);

    // ---------------
    // Update role
    // ---------------
    @ApiOperation(value = "API to update role")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Put Successfully")})
    @PutMapping(ApiUtils.roleId)
    ResponseEntity updateRole(@RequestBody RoleRequestDto roleRequestDto,
                              @PathVariable(value = "roleId") UUID roleId);
}
