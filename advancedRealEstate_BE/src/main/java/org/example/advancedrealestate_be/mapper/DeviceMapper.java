package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.request.DeviceRequest;
import org.example.advancedrealestate_be.dto.response.DeviceResponse;
import org.example.advancedrealestate_be.entity.Devices;
import org.example.advancedrealestate_be.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    @Autowired
    private BuildingRepository buildingRepository;

    public Devices toEntity(DeviceRequest request){
        Devices devices=new Devices();
        devices.setDevice_name(request.getDevice_name());
        devices.setInstallation_date(request.getInstallation_date());
        devices.setStatus(request.getStatus());
        devices.setPrice(request.getPrice());
        devices.setDescription(request.getDescription());

        if (request.getBuildingId() != null) {
            buildingRepository.findById(request.getBuildingId()).ifPresent(devices::setBuilding);
        }
        return devices;
    }

    public DeviceResponse toResponse(Devices entity){
        return DeviceResponse.builder()
                .id(entity.getId())
                .device_name(entity.getDevice_name())
                .installation_date(entity.getInstallation_date())
                .status(entity.getStatus())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .build();
    }
}
