package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.ServiceDto;

import java.util.List;

public interface ServiceService {
    List<ServiceDto> findAll();

    ServiceDto findById(String id);

    ServiceDto create(ServiceDto serviceDto);

    ServiceDto updateById(String id, ServiceDto buildingDto);

    ServiceDto deleteById(String id);
}
