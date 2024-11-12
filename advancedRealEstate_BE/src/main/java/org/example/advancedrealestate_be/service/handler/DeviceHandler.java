package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.request.DeviceRequest;
import org.example.advancedrealestate_be.dto.response.DeviceResponse;
import org.example.advancedrealestate_be.entity.Devices;
import org.example.advancedrealestate_be.mapper.DeviceMapper;
import org.example.advancedrealestate_be.repository.DeviceRepository;
import org.example.advancedrealestate_be.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceHandler implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public DeviceResponse createDevice(DeviceRequest request) {
        Devices devices = deviceMapper.toEntity(request); // Ensure toEntity uses java.time.LocalDate
        devices = deviceRepository.save(devices);
        return deviceMapper.toResponse(devices); // Ensure toResponse uses java.time.LocalDate
    }

    @Override
    public DeviceResponse getDeviceById(String id) {
        Devices devices = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found!"));
        return deviceMapper.toResponse(devices);
    }

    @Override
    public List<DeviceResponse> getAllDevice() {
        return deviceRepository.findAll()
                .stream()
                .map(deviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceResponse updateDevice(String id, DeviceRequest request) {
        Devices devices = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found!"));
        devices.setDevice_name(request.getDevice_name());
        devices.setInstallation_date(request.getInstallation_date()); // Ensure this is java.time.LocalDate
        devices.setStatus(request.getStatus());
        devices.setPrice(request.getPrice());
        devices.setDescription(request.getDescription());
        deviceRepository.save(devices);
        return deviceMapper.toResponse(devices);
    }

    @Override
    public void deleteDevice(String id) {
        deviceRepository.deleteById(id);
    }
}
