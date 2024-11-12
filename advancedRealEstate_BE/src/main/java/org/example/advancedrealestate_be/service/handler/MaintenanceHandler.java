package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.request.MaintenanceRequest;
import org.example.advancedrealestate_be.entity.Maintenances;
import org.example.advancedrealestate_be.mapper.MaintenanceMapper;
import org.example.advancedrealestate_be.repository.MaintenanceRepository;
import org.example.advancedrealestate_be.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceHandler implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceMapper maintenanceMapper;


    @Override
    public Maintenances createMaintenance(MaintenanceRequest request) {
        Maintenances maintenances=maintenanceMapper.toEntity(request);
        maintenances=maintenanceRepository.save(maintenances);
        return maintenanceMapper.toResponse(maintenances);
    }

    @Override
    public Maintenances getMaintenanceById(String id) {
        Maintenances maintenances=maintenanceRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Maintenance not found !"));
        return maintenanceMapper.toResponse(maintenances);


    }

    @Override
    public Maintenances updateMaintenance(String id, MaintenanceRequest request) {
          Maintenances maintenances=maintenanceRepository.findById(id)
                  .orElseThrow(()->new RuntimeException("Maintenance not found !"));
          maintenances.setMaintenance_date(request.getMaintenance_date());
          maintenances.setDescription(request.getDescription());
          maintenances.setCost(request.getCost());
         maintenanceRepository.save(maintenances);
         return maintenanceMapper.toResponse(maintenances);


    }

    @Override
    public void deleteMaintenance(String id) {
               maintenanceRepository.deleteById(id);
    }

    @Override
    public List<Maintenances> getAllMaintenance() {
        return maintenanceRepository.findAll()
                .stream()
                .map(maintenanceMapper::toResponse)
                .collect(Collectors.toList());
    }
}
