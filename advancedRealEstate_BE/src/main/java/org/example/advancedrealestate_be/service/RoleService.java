package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.RolePermissionRequest;
import org.example.advancedrealestate_be.dto.request.RoleRequest;
import org.example.advancedrealestate_be.dto.response.RolePermissionResponse;
import org.example.advancedrealestate_be.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    RolePermissionResponse updateRolePermission(String roleName, RolePermissionRequest request);
    List<RoleResponse> getAll();
   void delete(String role);
}
