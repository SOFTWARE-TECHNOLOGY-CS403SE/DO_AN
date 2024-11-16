package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.MaintenanceRequest;
import org.example.advancedrealestate_be.dto.response.MaintenanceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaintenanceService {
    MaintenanceResponse createMaintenance(MaintenanceRequest request);
    MaintenanceResponse getMaintenanceById(String id);
    MaintenanceResponse updateMaintenance(String id, MaintenanceRequest request);
    MaintenanceResponse deleteMaintenance(String id);
    Page<MaintenanceResponse> getAllMaintenance(int page, int size);

    void deleteAllMaintenance(List<String> ids);
}
