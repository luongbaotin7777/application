package com.init.application.controller.user;

import com.init.application.common.ApiUtils;
import com.init.application.dto.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Api(tags = "User")
@RequestMapping(value = ApiUtils.USER, produces = MediaType.APPLICATION_JSON_VALUE)
public interface IUserController {
    // ---------------
    // Create new user by admin
    // ---------------
    @ApiOperation(value = "API to create new user by admin")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Post SuccessFully")})
    @PostMapping("/create")
    ResponseEntity createUserByAdmin(@RequestBody AdminCreateDto adminCreateDto);

    // ---------------
    // Register USer
    // ---------------
    @ApiOperation(value = "API to register user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Post SuccessFully")})
    @PostMapping("/register")
    ResponseEntity registerUser(UserRequestDto userRequestDto);

    // ---------------
    // Get list user
    // ---------------
    @ApiOperation(value = "API to get list user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @GetMapping
    ResponseEntity getListUser(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                               @RequestParam(name = "sortType", required = false, defaultValue = "ASC") String sortType,
                               @RequestParam(name = "sortBy", required = false, defaultValue = "createdTime") String sortBy);

    // ---------------
    // Get detail user
    // ---------------
    @ApiOperation(value = "API to get detail user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @GetMapping(ApiUtils.userId)
    ResponseEntity getDetailUser(@PathVariable(value = "userId") UUID userId);

    // ---------------
    // Update User
    // ---------------
    @ApiOperation(value = "API to update user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @PutMapping(ApiUtils.userId)
    ResponseEntity updateUser(@PathVariable(value = "userId") UUID userId, @RequestBody UserUpdateDto userUpdateDto);

    // ---------------
    // Update User By Admin
    // ---------------
    @ApiOperation(value = "API to update user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @PutMapping("/admin" + ApiUtils.userId)
    ResponseEntity updateUserByAdmin(@PathVariable(value = "userId") UUID userId,
                                     @RequestBody UpdateUserByAdminDto updateUserByAdminDto);

    // ---------------
    // Login
    // ---------------
    @ApiOperation(value = "API to login")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @PostMapping("/login")
    ResponseEntity login(LoginRequestDto loginDto);

    // ---------------
    // Logout
    // ---------------
    @ApiOperation(value = "API to logout")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @PostMapping("/logout")
    ResponseEntity logout(HttpServletRequest request);

    // ---------------
    // Refresh Token
    // ---------------
    @ApiOperation(value = "API to refresh token")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get SuccessFully")})
    @PostMapping("/refresh-token")
    ResponseEntity refreshToken(String refreshToken);
}
