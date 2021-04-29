package com.init.application.service.role;

import com.init.application.common.DataException;
import com.init.application.dto.role.RoleRequestDto;
import com.init.application.dto.role.RoleResponseDto;
import com.init.application.entity.RoleEntity;
import com.init.application.entity.RolePermissionEntity;
import com.init.application.entity.UserRoleEntity;
import com.init.application.generic.BaseService;
import com.init.application.handler_exception.RestError;
import com.init.application.handler_exception.SingleErrorException;
import com.init.application.mapper.IRoleMapper;
import com.init.application.repository.IRoleRepository;
import com.init.application.service.permission.IPermissionService;
import com.init.application.service.rolepermission.IRolePermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RoleService extends BaseService<RoleEntity, UUID> implements IRoleService {
    private final IRoleRepository roleRepository;
    private final IRoleMapper roleMapper;

    private final IRolePermissionService rolePermissionService;
    private final IPermissionService permissionService;

    public RoleService(IRoleRepository roleRepository, IRoleMapper roleMapper,
                       IRolePermissionService rolePermissionService, IPermissionService permissionService) {
        super(roleRepository);
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.rolePermissionService = rolePermissionService;
        this.permissionService = permissionService;
    }

    @Override
    public RoleEntity create(RoleRequestDto roleRequestDto) {
        RoleEntity roleEntity = roleMapper.toRoleEntity(roleRequestDto);
        if (roleRepository.existsByRoleName(roleRequestDto.getRoleName())) {
            RestError err = RestError.builder()
                    .message(DataException.RoleAlreadyExist.getValue())
                    .resource(UserRoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.BAD_REQUEST);
        }
        roleRepository.save(roleEntity);
        return addPermissions(roleEntity, roleRequestDto.getPermissionIds());
    }

    private RoleEntity addPermissions(RoleEntity roleEntity, List<UUID> permissionIds) {
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionService.findAllByRoleId(roleEntity.getId());
        if (!rolePermissionEntities.isEmpty()) {
            rolePermissionService.deleteInBatch(rolePermissionEntities);
        }
        if (permissionIds != null) {
            for (UUID permissionId : permissionIds) {
                if (!permissionService.existById(permissionId)) {
                    RestError err = RestError.builder()
                            .message(DataException.DoesNotExistPermission.getValue())
                            .resource(UserRoleEntity.class.toString())
                            .build();
                    throw new SingleErrorException(err, HttpStatus.BAD_REQUEST);
                }
                RolePermissionEntity rolePermissionEntity = RolePermissionEntity.builder()
                        .roleId(roleEntity.getId())
                        .permissionId(permissionId)
                        .build();

                rolePermissionService.create(rolePermissionEntity);
            }
        }

        return roleEntity;
    }

    @Override
    public Map<String, Object> getListRole(Integer page, Integer size, String sortType, String sortBy) {
        Map<String, Object> results = this.findAllPage(page, size, sortType, sortBy);

        List<RoleEntity> roleEntities = (List<RoleEntity>) results.get("resultContent");
        List<RoleResponseDto> responseDtos = roleMapper.toRoleResponseDtos(roleEntities);

        results.remove("resultContent");
        results.put("listRoles", responseDtos);

        return results;
    }

    @Override
    public boolean existById(UUID roleId) {
        return roleRepository.existsById(roleId);
    }

    @Override
    public RoleResponseDto findById(UUID roleId) {
        return roleMapper.toRoleResponseDto(roleRepository.findById(roleId).orElseThrow(()
                -> new SingleErrorException(RestError.builder()
                .message(DataException.DoesNotExistRole.getValue())
                .resource(RoleEntity.class.toString())
                .build(), HttpStatus.BAD_REQUEST)));
    }

    @Override
    public RoleEntity updateRole(RoleRequestDto roleRequestDto, UUID roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(()
                -> new SingleErrorException(RestError.builder()
                .message(DataException.DoesNotExistRole.getValue())
                .resource(RoleEntity.class.toString())
                .build(), HttpStatus.BAD_REQUEST));

        checkRoleAlreadyExist(roleRequestDto, roleEntity);

        roleEntity.setRoleName(roleRequestDto.getRoleName());
        roleEntity.setDescription(roleRequestDto.getDescription());

        return addPermissions(roleEntity, roleRequestDto.getPermissionIds());
    }

    @Override
    public RoleEntity findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    private void checkRoleAlreadyExist(RoleRequestDto roleRequestDto, RoleEntity roleEntity) {
        if (!roleEntity.getRoleName().equals(roleRequestDto.getRoleName())
                && roleRepository.existsByRoleName(roleRequestDto.getRoleName())) {
            RestError err = RestError.builder()
                    .message(DataException.RoleAlreadyExist.getValue())
                    .resource(RoleEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.NOT_FOUND);
        }
    }
}
