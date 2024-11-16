package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.request.MaintenanceRequest;
import org.example.advancedrealestate_be.dto.response.MaintenanceResponse;
import org.example.advancedrealestate_be.entity.Maintenances;
import org.example.advancedrealestate_be.mapper.MaintenanceMapper;
import org.example.advancedrealestate_be.repository.MaintenanceRepository;
import org.example.advancedrealestate_be.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public MaintenanceResponse createMaintenance(MaintenanceRequest request) {
        Maintenances maintenances=maintenanceMapper.toEntity(request);
        maintenances=maintenanceRepository.save(maintenances);
        return maintenanceMapper.toResponse(maintenances);
    }

    @Override
    public MaintenanceResponse getMaintenanceById(String id) {
        Maintenances maintenances=maintenanceRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Maintenance not found !"));
        return maintenanceMapper.toResponse(maintenances);


    }

    @Override
    public MaintenanceResponse updateMaintenance(String id, MaintenanceRequest request) {
          Maintenances maintenances=maintenanceRepository.findById(id)
                  .orElseThrow(()->new RuntimeException("Maintenance not found !"));
          maintenances.setMaintenance_date(request.getMaintenance_date());
          maintenances.setDescription(request.getDescription());
          maintenances.setCost(request.getCost());
         maintenanceRepository.save(maintenances);
         return maintenanceMapper.toResponse(maintenances);


    }

    @Override
    public MaintenanceResponse deleteMaintenance(String id) {
               maintenanceRepository.deleteById(id);
        return null;
    }

//    @Override
//    public List<Maintenances> getAllMaintenance() {
//        return maintenanceRepository.findAll()
//                .stream()
//                .map(maintenanceMapper::toResponse)
//                .collect(Collectors.toList());
//    }


//    @Override
//    public Page<MaintenanceResponse> getAllMaintenance(int page, int size) {
//        Pageable pageable = PageRequest.of(page - 1, size);
//        Page<Maintenances> maintenancesPage = maintenanceRepository.findAll(pageable);
//
//        List<MaintenanceResponse> maintenanceResponses= maintenancesPage.getContent().stream()
//                .map(maintenanceMapper::toResponse)
//                .collect(Collectors.toList());
//
//        // Tạo đối tượng Page<TypeBuildingResponse> từ List<TypeBuildingResponse> và thông tin phân trang của Page<User>
//        return new PageImpl<>(maintenanceResponses, pageable, maintenancesPage.getTotalElements());
//    }

    @Override
    public Page<MaintenanceResponse> getAllMaintenance(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Maintenances> maintenancesPage = maintenanceRepository.findAll(pageable);

        // Correctly map from Maintenances to MaintenanceResponse
        List<MaintenanceResponse> maintenanceResponses = maintenancesPage.getContent().stream()
                .map(maintenanceMapper::toResponse) // Ensure this maps correctly to MaintenanceResponse
                .collect(Collectors.toList());

        // Create Page<MaintenanceResponse> from the mapped list
        return new PageImpl<>(maintenanceResponses, pageable, maintenancesPage.getTotalElements());
    }

    @Override
    public void deleteAllMaintenance(List<String> ids) {
        List<Maintenances> maintenanceResponses=maintenanceRepository.findAllById(ids);
        if(maintenanceResponses.size()!=ids.size()){
            throw new RuntimeException("Some maintenance form not found !");
        }
        maintenanceRepository.deleteAll(maintenanceResponses);
    }


}
