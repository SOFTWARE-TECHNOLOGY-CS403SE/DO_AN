package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.request.MaintenanceRequest;
import org.example.advancedrealestate_be.entity.Maintenances;
import org.example.advancedrealestate_be.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceMapper {
    @Autowired
    private BuildingRepository buildingRepository;
    public Maintenances toEntity(MaintenanceRequest request){
        Maintenances maintenances=new Maintenances();
        maintenances.setMaintenance_date(request.getMaintenance_date());
        maintenances.setDescription(request.getDescription());
        maintenances.setCost(request.getCost());

        buildingRepository.findById(request.getBuildingId()).ifPresent(maintenances::setBuilding);
        return maintenances;
    }

    public Maintenances toResponse(Maintenances entity){
        return Maintenances.builder()
                .id(entity.getId())
                .maintenance_date(entity.getMaintenance_date())
                .cost(entity.getCost())
                .description(entity.getDescription())
                .build();
    }



}