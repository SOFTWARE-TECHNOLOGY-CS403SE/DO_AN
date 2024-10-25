package org.example.advancedrealestate_be.service;

import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.PermissionRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;

import java.util.List;

public interface PermistionService {
    PermissionResponse create(PermissionRequest request);

    JSONObject updateById(String permissionName, PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String permission);

}
