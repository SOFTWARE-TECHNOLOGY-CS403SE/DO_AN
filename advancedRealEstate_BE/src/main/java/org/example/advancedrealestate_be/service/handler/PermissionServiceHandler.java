package org.example.advancedrealestate_be.service.handler;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.PermissionRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;
import org.example.advancedrealestate_be.entity.Permission;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.PermissionMapper;
import org.example.advancedrealestate_be.repository.PermissionRepository;
import org.example.advancedrealestate_be.service.PermistionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceHandler implements PermistionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionMapper permissionMapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public JSONObject updateById(String permissionName , PermissionRequest request) {

        Permission permission = permissionRepository.findById(permissionName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PermissionResponse oldPermissionResponse = new PermissionResponse(
                permission.getName(),
                permission.getDescription()
        );
        permission.setDescription(request.getDescription());
        Permission permissionUpdate = permissionRepository.save(permission);
        JSONObject updateObjectResponse = new JSONObject();
        PermissionResponse permissionUpdateResponse = new PermissionResponse(
            permissionUpdate.getName(),
            permissionUpdate.getDescription()
        );
        updateObjectResponse.put("permissionUpdated", permissionUpdateResponse);
        updateObjectResponse.put("oldPermission", oldPermissionResponse);
        return updateObjectResponse;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
