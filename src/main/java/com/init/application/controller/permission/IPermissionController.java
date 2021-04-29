package com.init.application.controller.permission;

import com.init.application.common.ApiUtils;
import com.init.application.dto.permission.PermissionRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Permission")
@RequestMapping(value = ApiUtils.Permission, produces = MediaType.APPLICATION_JSON_VALUE)
public interface IPermissionController {
    // ---------------
    // Create new permission
    // ---------------
    @ApiOperation(value = "API to create new permission")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Post Successfully")})
    @PostMapping
    ResponseEntity create(@RequestBody PermissionRequestDto permissionRequestDto);

    // ---------------
    // Get list permission
    // ---------------
    @ApiOperation(value = "API to get list permission")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Successfully")})
    @GetMapping
    ResponseEntity getListPermission(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                     @RequestParam(name = "sortType", required = false, defaultValue = "ASC") String sortType,
                                     @RequestParam(name = "sortBy", required = false, defaultValue = "createdTime") String sortBy);

    // ---------------
    // Get detail permission
    // ---------------
    @ApiOperation(value = "API to get detail permission")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Successfully")})
    @GetMapping(ApiUtils.permissionId)
    ResponseEntity getDetailPermission(@PathVariable(value = "permissionId") UUID permissionId);

    // ---------------
    // Update permission
    // ---------------
    @ApiOperation(value = "API to update permission")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Put Successfully")})
    @PutMapping(ApiUtils.permissionId)
    ResponseEntity updatePermission(@PathVariable(value = "permissionId") UUID permissionId, @RequestBody PermissionRequestDto permissionRequestDto);
}
