package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.DeviceRequest;
import org.example.advancedrealestate_be.dto.response.DeviceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeviceService {
    DeviceResponse createDevice(DeviceRequest request);
    DeviceResponse getDeviceById(String id);
    List<DeviceResponse> getAllDevice();
    DeviceResponse updateDevice(String id, DeviceRequest request);
    void deleteDevice(String id);
    Page<DeviceResponse> getDevice(int page, int size);
}
