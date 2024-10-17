package org.example.advancedrealestate_be.service.handler;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrealestate_be.dto.request.PermissionRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;
import org.example.advancedrealestate_be.entity.Permission;
import org.example.advancedrealestate_be.mapper.PermissionMapper;
import org.example.advancedrealestate_be.repository.PermissionRepository;
import org.example.advancedrealestate_be.service.PermistionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
