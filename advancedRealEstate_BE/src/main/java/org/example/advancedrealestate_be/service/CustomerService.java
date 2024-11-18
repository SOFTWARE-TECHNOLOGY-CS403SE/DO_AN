package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.CustomerRequest;
import org.example.advancedrealestate_be.dto.response.CustomerResponse;
import org.example.advancedrealestate_be.dto.response.TypeBuildingResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
     String deleteCustomer(String id);


     String updateCustomer(String id, CustomerRequest request);

     String createCustomer(CustomerRequest request);

     Page<CustomerResponse> getAllCustomers(int page, int size);

     CustomerResponse getCustomerById(String id);
//     List<CustomerResponse> getAllCustomers();
     String deleteCustomers(List<String> ids);
}
