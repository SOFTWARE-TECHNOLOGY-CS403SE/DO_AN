package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.Contracts;
import org.example.advancedrealestate_be.entity.Customers;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.mapper.ContractMapper;
import org.example.advancedrealestate_be.repository.BuildingRepository;
import org.example.advancedrealestate_be.repository.ContractReposetory;
import org.example.advancedrealestate_be.repository.CustomersRepository;
import org.example.advancedrealestate_be.repository.UserRepository;
import org.example.advancedrealestate_be.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractHandler implements ContractService {
//    @Autowired
//    private ContractReposetory contractsRepository;
//
//    @Autowired
//    private CustomersRepository customersRepository;
//
//    @Autowired
//    private BuildingRepository buildingRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public ContractResponse createContract(ContractRequest request) {
//        Contracts contract = new Contracts();
//        mapRequestToContract(request, contract);
//        Contracts savedContract = contractsRepository.save(contract);
//        return mapContractToResponse(savedContract);
//    }
//
//    @Override
//    public ContractResponse updateContract(String id, ContractRequest request) {
//        Contracts contract = contractsRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Contract not found"));
//        mapRequestToContract(request, contract);
//        Contracts updatedContract = contractsRepository.save(contract);
//        return mapContractToResponse(updatedContract);
//    }
//
//    @Override
//    public ContractResponse getContractById(String id) {
//        Contracts contract = contractsRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Contract not found"));
//        return mapContractToResponse(contract);
//    }
//
//    @Override
//    public List<ContractResponse> getAllContracts() {
//        return contractsRepository.findAll().stream()
//                .map(this::mapContractToResponse)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteContract(String id) {
//        contractsRepository.deleteById(id);
//    }
//
//    @Override
//    public void mapRequestToContract(ContractRequest request, Contracts contract) {
//        contract.setContract_name(request.getContractName());
//        contract.setContract_details(request.getContractDetails());
//
//        Customers customer = customersRepository.findById(request.getCustomerId())
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//        contract.setCustomer(customer);
//
//        Building building = buildingRepository.findById(request.getBuildingId())
//                .orElseThrow(() -> new RuntimeException("Building not found"));
//        contract.setBuilding(building);
//
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        contract.setUser(user);
//    }
//
//    public ContractResponse mapContractToResponse(Contracts contract) {
//        ContractResponse response = new ContractResponse();
//        response.setId(contract.getId());
//        response.setContractName(contract.getContract_name());
//        response.setContractDetails(contract.getContract_details());
//        response.setCustomerName(contract.getCustomer().getFirst_name() + " " + contract.getCustomer().getLast_name());
//        response.setBuildingName(contract.getBuilding().getName());
//        response.setUserName(contract.getUser().getUsername());
//        response.setTransactionIds(contract.getTransactions().stream().map(t -> t.getId()).collect(Collectors.toList()));
//        response.setInvoiceIds(contract.getInvoices().stream().map(i -> i.getId()).collect(Collectors.toList()));
//        return response;
//    }

    @Autowired
    private ContractReposetory contractRepository;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;
    // Create a new contract

    @Override
    public ContractResponse createContract(ContractRequest request) {
//        Contracts contract = contractMapper.toEntity(request);
//        contract = contractRepository.save(contract);
//        return contractMapper.toResponse(contract);
        // Map to entity
        Contracts contract = contractMapper.toEntity(request);





        Customers customer = customersRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        contract.setCustomer(customer);

        Building building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found"));
        contract.setBuilding(building);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        contract.setUser(user);

        // Set the entities to the contract
        contract.setCustomer(customer);
        contract.setBuilding(building);
        contract.setUser(user);
        contract=contractRepository.save(contract);
        // Save the contract entity
        return contractMapper.toResponse(contract);
    }

    // Get a contract by ID
    @Override
    public ContractResponse getContractById(String id) {
        return contractRepository.findById(id)
                .map(contractMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
    }

    // Update a contract by ID
    @Override
    public ContractResponse updateContract(String id, ContractRequest request) {
        Contracts contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        contract.setContract_name(request.getContractName());
        contract.setContract_details(request.getContractDetails());
        // Set other fields based on the request data as necessary

        contract = contractRepository.save(contract);
        return contractMapper.toResponse(contract);
    }

    // Delete a contract by ID
    @Override
    public void deleteContract(String id) {
        contractRepository.deleteById(id);
    }

    // List all contracts
    @Override
    public List<ContractResponse> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(contractMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Add this method to your ContractHandler class
    @Override
    public void deleteContracts(List<String> ids) {
        List<Contracts> contractsToDelete = contractRepository.findAllById(ids);
        if (contractsToDelete.size() != ids.size()) {
            throw new RuntimeException("Some contracts not found");
        }
        contractRepository.deleteAll(contractsToDelete);
    }



}