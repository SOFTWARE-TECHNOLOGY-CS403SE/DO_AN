package org.example.advancedrealestate_be.mapper;


import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;
import org.example.advancedrealestate_be.entity.Contract_details;
import org.example.advancedrealestate_be.repository.ContractDetailsRepository;
import org.example.advancedrealestate_be.repository.ContractReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ContractDetailMapper {

    @Autowired
    private ContractReposetory contractReposetory;

    public Contract_details toEntity(ContractDetailRequest request) {
        Contract_details contractDetails = new Contract_details();
        contractDetails.setDescription(request.getDescription());
        contractDetails.setNote(request.getNote());
        contractDetails.setAmount(request.getAmount());

        // Fetch the related contract and add it to the contractsList
        contractReposetory.findById(request.getContractId()).ifPresent(contract -> {
            // Initialize contractsList if it is null
            if (contractDetails.getContractsList() == null) {
                contractDetails.setContractsList(new ArrayList<>());
            }
            // Add the fetched contract to the contractsList
            contractDetails.getContractsList().add(contract);
        });
        return contractDetails;
    }

    public ContractDetailResponse toResponse(Contract_details entity) {
        return ContractDetailResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .note(entity.getNote())
                .amount(entity.getAmount())
                .build();
    }
}

