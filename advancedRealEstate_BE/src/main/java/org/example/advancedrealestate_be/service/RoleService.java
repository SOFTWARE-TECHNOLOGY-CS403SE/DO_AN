package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.RoleRequest;
import org.example.advancedrealestate_be.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    public RoleResponse create(RoleRequest request);
    public List<RoleResponse> getAll();
    public void delete(String role);
}
