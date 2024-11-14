package org.example.advancedrealestate_be.service.handler;


import org.example.advancedrealestate_be.dto.request.CustomerRequest;
import org.example.advancedrealestate_be.dto.response.CustomerResponse;
import org.example.advancedrealestate_be.entity.Customers;
import org.example.advancedrealestate_be.mapper.CustomerMapper;
import org.example.advancedrealestate_be.repository.CustomersRepository;
import org.example.advancedrealestate_be.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerHandler implements CustomerService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public CustomerResponse deleteCustomer(String id) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(String id, CustomerRequest request) {
        Customers customers=customersRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Customer not found !"));
        if(!passwordEncoder.matches(request.getPassword(), customers.getPassword())){
            throw new RuntimeException("Incorrect password !");
        }

        if(!request.getEmail().equals(customers.getEmail())){
            throw new RuntimeException("Incorect email !");
        }

        customerMapper.updateEntityFromRequest(request,customers);
        if(request.getNewPassword() !=null && !request.getNewPassword().isEmpty()){
               customers.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        Customers updateCustomer=customersRepository.save(customers);
        return customerMapper.toResponse(updateCustomer);


    }



    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
      if(customersRepository.findByUser_nameOrEmail(request.getUser_name(),request.getEmail()).isPresent()){
          throw new RuntimeException("User already exists");
      }
      String encodedPassword =passwordEncoder.encode(request.getPassword());
      Customers newCustomer=customerMapper.toEntity(request,encodedPassword);
      Customers savedCustomer=customersRepository.save(newCustomer);
      return customerMapper.toResponse(savedCustomer);

    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customers> customers = customersRepository.findAll();
        return customers.stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCustomers(List<String> ids) {
        List<Customers> customers = customersRepository.findAllById(ids);
        if (customers.size() != ids.size()) {
            throw new RuntimeException("Some customers not found");
        }
        customersRepository.deleteAll(customers);
    }
}
