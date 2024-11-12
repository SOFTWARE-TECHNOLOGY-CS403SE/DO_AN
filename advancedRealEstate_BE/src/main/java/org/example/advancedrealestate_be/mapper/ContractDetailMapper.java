package org.example.advancedrealestate_be.mapper;


import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;
import org.example.advancedrealestate_be.entity.Contract_details;
import org.example.advancedrealestate_be.repository.ContractReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractDetailMapper {

    @Autowired
    private ContractReposetory contractReposetory;

    public Contract_details toEntity(ContractDetailRequest request) {
        Contract_details contractDetails = new Contract_details();
        contractDetails.setDescription(request.getDescription());
        contractDetails.setNote(request.getNote());
        contractDetails.setAmount(request.getAmount());

        // Fetch and set the related contract
        contractReposetory.findById(request.getContractId()).ifPresent(contractDetails::setContract);
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
