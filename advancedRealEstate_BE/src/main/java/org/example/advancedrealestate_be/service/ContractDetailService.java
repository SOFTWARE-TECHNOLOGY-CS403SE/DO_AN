package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;

import java.util.List;

public interface ContractDetailService {
    String createContractDetail(ContractDetailRequest request);
    ContractDetailResponse getContractDetailById(String id);
    List<ContractDetailResponse> getAllContractDetails();
    String updateContractDetail(String id, ContractDetailRequest request);
    String deleteContractDetail(String id);
}
