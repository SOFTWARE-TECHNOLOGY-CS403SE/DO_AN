package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;
import org.example.advancedrealestate_be.entity.Contract_details;
import org.example.advancedrealestate_be.mapper.ContractDetailMapper;
import org.example.advancedrealestate_be.repository.ContractDetailsRepository;
import org.example.advancedrealestate_be.service.ContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractDetailHandler implements ContractDetailService {

    @Autowired
    private ContractDetailsRepository contractDetailsRepository;

    @Autowired
    private ContractDetailMapper contractDetailMapper;



    @Override
    public ContractDetailResponse createContractDetail(ContractDetailRequest request) {
        Contract_details contractDetail = contractDetailMapper.toEntity(request);
        contractDetail = contractDetailsRepository.save(contractDetail);
        return contractDetailMapper.toResponse(contractDetail);
    }

    @Override
    public ContractDetailResponse getContractDetailById(String id) {
        Contract_details contractDetail = contractDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContractDetail not found"));
        return contractDetailMapper.toResponse(contractDetail);
    }

    @Override
    public List<ContractDetailResponse> getAllContractDetails() {
        return contractDetailsRepository.findAll()
                .stream()
                .map(contractDetailMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ContractDetailResponse updateContractDetail(String id, ContractDetailRequest request) {
        Contract_details contractDetail = contractDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContractDetail not found"));
        contractDetail.setDescription(request.getDescription());
        contractDetail.setNote(request.getNote());
        contractDetail.setAmount(request.getAmount());
        contractDetailsRepository.save(contractDetail);
        return contractDetailMapper.toResponse(contractDetail);
    }

    @Override
    public void deleteContractDetail(String id) {
        contractDetailsRepository.deleteById(id);
    }


}
