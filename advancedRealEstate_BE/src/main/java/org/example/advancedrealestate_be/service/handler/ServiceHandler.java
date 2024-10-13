package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.constant.ErrorEnumConstant;
import org.example.advancedrealestate_be.dto.ServiceDto;
import org.example.advancedrealestate_be.entity.Service;
import org.example.advancedrealestate_be.mapper.ServiceMapper;
import org.example.advancedrealestate_be.repository.ServiceRepository;
import org.example.advancedrealestate_be.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceHandler implements ServiceService {
    @Autowired
    ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceHandler(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ServiceDto> findAll() {
        List<Service> serviceList = serviceRepository.findAll();
        return serviceList.stream().map(ServiceMapper::mapToService).collect(Collectors.toList());
    }

    @Override
    public ServiceDto findById(String id) {
        Optional<org.example.advancedrealestate_be.entity.Service> service = serviceRepository.findById(id);
        if (service.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.ServiceNotFound.toString());
        }
        return service.map(value -> new ServiceDto(value.getId(), value.getName(), value.getPrice())).orElse(null);
    }

    @Transactional
    @Override
    public ServiceDto create(ServiceDto serviceDto) {
        Service service = modelMapper.map(serviceDto, Service.class);
        Service serviceNew = serviceRepository.save(service);
        return new ServiceDto(serviceNew.getId(), serviceNew.getName(), serviceNew.getPrice());
    }

    @Override
    public ServiceDto updateById(String id, ServiceDto serviceDto) {
        Optional<org.example.advancedrealestate_be.entity.Service> service = serviceRepository.findById(id);
        if (service.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        service.get().setName(serviceDto.getName() != null ? serviceDto.getName() : service.get().getName());
        service.get().setPrice(serviceDto.getPrice() != 0 ? serviceDto.getPrice() : service.get().getPrice());
        org.example.advancedrealestate_be.entity.Service serviceUpdate = serviceRepository.save(service.get());
        return new ServiceDto(serviceUpdate.getId(), serviceUpdate.getName(), serviceUpdate.getPrice());
    }

    @Override
    public ServiceDto deleteById(String id) {
        Optional<org.example.advancedrealestate_be.entity.Service> service = serviceRepository.findById(id);
        if (service.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.ServiceNotFound.toString());
        }
        ServiceDto serviceDto = new ServiceDto(service.get().getId(), service.get().getName(), service.get().getPrice());
        serviceRepository.delete(service.get());
        return serviceDto;
    }
}
