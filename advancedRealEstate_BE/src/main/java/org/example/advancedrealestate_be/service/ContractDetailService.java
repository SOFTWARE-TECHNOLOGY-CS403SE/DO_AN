package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;

import java.util.List;

public interface ContractDetailService {
    ContractDetailResponse createContractDetail(ContractDetailRequest request);
    ContractDetailResponse getContractDetailById(String id);
    List<ContractDetailResponse> getAllContractDetails();
    ContractDetailResponse updateContractDetail(String id, ContractDetailRequest request);
    void deleteContractDetail(String id);
}
