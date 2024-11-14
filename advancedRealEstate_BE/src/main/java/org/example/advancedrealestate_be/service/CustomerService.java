package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.CustomerRequest;
import org.example.advancedrealestate_be.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
     CustomerResponse deleteCustomer(String id);


     CustomerResponse updateCustomer(String id, CustomerRequest request);

     CustomerResponse createCustomer(CustomerRequest request);



     CustomerResponse getCustomerById(String id);
     List<CustomerResponse> getAllCustomers();
     void deleteCustomers(List<String> ids);
}
