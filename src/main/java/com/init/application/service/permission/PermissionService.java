package com.init.application.service.permission;

import com.init.application.common.DataException;
import com.init.application.dto.permission.PermissionRequestDto;
import com.init.application.dto.permission.PermissionResponseDto;
import com.init.application.entity.PermissionEntity;
import com.init.application.generic.BaseService;
import com.init.application.handler_exception.RestError;
import com.init.application.handler_exception.SingleErrorException;
import com.init.application.mapper.IPermissionMapper;
import com.init.application.repository.IPermissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PermissionService extends BaseService<PermissionEntity, UUID> implements IPermissionService {
    private final IPermissionRepository permissionRepository;
    private final IPermissionMapper permissionMapper;

    public PermissionService(IPermissionRepository permissionRepository, IPermissionMapper permissionMapper) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PermissionEntity create(PermissionRequestDto permissionRequestDto) {
        PermissionEntity permissionEntity = permissionMapper.toPermissionEntity(permissionRequestDto);
        if (permissionRepository.existsByPermissionName(permissionEntity.getPermissionName())) {
            RestError restError = RestError.builder()
                    .message(DataException.PermissionAlreadyExist.getValue())
                    .resource(PermissionEntity.class.toString())
                    .build();
            throw new SingleErrorException(restError, HttpStatus.BAD_REQUEST);
        }
        return permissionRepository.save(permissionEntity);
    }

    @Override
    public Map<String, Object> getListPermission(Integer page, Integer size, String sortType, String sortBy) {
        Map<String, Object> results = this.findAllPage(page, size, sortType, sortBy);

        List<PermissionEntity> permissionEntities = (List<PermissionEntity>) results.get("resultContent");
        List<PermissionResponseDto> responseDtos = permissionMapper.toPermissionResponseDtos(permissionEntities);

        results.remove("resultContent");
        results.put("listPermision", responseDtos);

        return results;
    }

    @Override
    public boolean existById(UUID permissionId) {
        return permissionRepository.existsById(permissionId);
    }

    @Override
    public PermissionResponseDto findById(UUID permissionId) {
        return permissionMapper.toPermissionResponseDto(permissionRepository.findById(permissionId).orElseThrow(()
                -> new SingleErrorException(RestError.builder()
                .message(DataException.DoesNotExistPermission.getValue())
                .resource(PermissionEntity.class.toString())
                .build(), HttpStatus.BAD_REQUEST)));
    }

    @Override
    public PermissionEntity updatePermission(UUID permissionId, PermissionRequestDto permissionRequestDto) {
        PermissionEntity permissionEntity = permissionRepository.findById(permissionId).orElseThrow(()
                -> new SingleErrorException(RestError.builder()
                .message(DataException.DoesNotExistRole.getValue())
                .resource(PermissionEntity.class.toString())
                .build(), HttpStatus.BAD_REQUEST));

        checkPermissionAlreadyExist(permissionRequestDto, permissionEntity);

        permissionEntity.setPermissionName(permissionRequestDto.getPermissionName());
        permissionEntity.setDescription(permissionRequestDto.getDescription());
        permissionEntity.setGroupPermission(permissionRequestDto.getGroupPermission());

        return permissionRepository.save(permissionEntity);
    }

    private void checkPermissionAlreadyExist(PermissionRequestDto permissionRequestDto, PermissionEntity permissionEntity) {
        if (!permissionEntity.getPermissionName().equals(permissionRequestDto.getPermissionName())
                && permissionRepository.existsByPermissionName(permissionRequestDto.getPermissionName())) {
            RestError err = RestError.builder()
                    .message(DataException.PermissionAlreadyExist.getValue())
                    .resource(PermissionEntity.class.toString())
                    .build();
            throw new SingleErrorException(err, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public PermissionEntity findByPermissionName(String permissionName) {
        return permissionRepository.findByPermissionName(permissionName);
    }
}
