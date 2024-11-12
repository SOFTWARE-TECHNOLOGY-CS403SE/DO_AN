package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.MaintenanceRequest;
import org.example.advancedrealestate_be.entity.Maintenances;

import java.util.List;

public interface MaintenanceService {
    Maintenances createMaintenance(MaintenanceRequest request);
    Maintenances getMaintenanceById(String id);
    Maintenances updateMaintenance(String id, MaintenanceRequest request);
    void deleteMaintenance(String id);
    List<Maintenances> getAllMaintenance();
}
