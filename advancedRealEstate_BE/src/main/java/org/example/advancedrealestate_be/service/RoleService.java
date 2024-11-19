package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.CreateRolePermissionRequest;
import org.example.advancedrealestate_be.dto.request.DeleteRoleRequest;
import org.example.advancedrealestate_be.dto.request.RoleCreationRequest;
import org.example.advancedrealestate_be.dto.request.RoleUpdateRequest;
import org.example.advancedrealestate_be.dto.response.BuildingResponse;
import org.example.advancedrealestate_be.dto.response.RoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    String createRole(RoleCreationRequest request);
    String createRolePermission(CreateRolePermissionRequest request);
    String updateRole(String userId, RoleUpdateRequest request);
    String deleteRole(String RoleId);
    Page<RoleResponse> getBuilding(int page, int size);
    String deleteRoles(DeleteRoleRequest request);
    List<RoleResponse> getAllRoles();
}
