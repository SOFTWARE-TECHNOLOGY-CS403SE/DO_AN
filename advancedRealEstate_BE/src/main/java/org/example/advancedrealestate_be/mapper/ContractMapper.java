package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.entity.Contracts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.entity.Contracts;
import org.example.advancedrealestate_be.entity.Contract_details;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.entity.Contracts;
import org.example.advancedrealestate_be.entity.Contract_details;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "contractDetails", target = "contractDetails")
    ContractResponse toResponse(Contracts contract);

    @Mapping(target = "customer", ignore = true) // Assume these are set manually in service
    @Mapping(target = "building", ignore = true)
    @Mapping(target = "user", ignore = true)
    Contracts toEntity(ContractRequest request);

    // Custom mapping method to convert Contract_details to a String
    default String map(Contract_details value) {
        // Example: Convert Contract_details to a string representation
        return value != null ? value.getDescription() + " - Amount: " + value.getAmount() : null;
    }
}


