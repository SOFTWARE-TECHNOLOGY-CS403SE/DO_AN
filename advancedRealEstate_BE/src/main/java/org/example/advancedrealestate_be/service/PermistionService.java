package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.PermissionRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;

import java.util.List;

public interface PermistionService {
    public PermissionResponse create(PermissionRequest request);
    public List<PermissionResponse> getAll();
    public void delete(String permission);

}
