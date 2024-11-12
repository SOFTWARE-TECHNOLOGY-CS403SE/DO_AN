package org.example.advancedrealestate_be.service.handler;


import org.example.advancedrealestate_be.entity.Customers;
import org.example.advancedrealestate_be.repository.CustomersRepository;
import org.example.advancedrealestate_be.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerHandler implements CustomerService {
    @Autowired
    private CustomersRepository customersRepository;

    @Override
    public List<Customers> getAllCustomers() {
        return customersRepository.findAll();
    }

    @Override
    public Optional<Customers> getCustomerById(String id) {
        return customersRepository.findById(id);
    }

    @Override

    public Customers createCustomer(Customers customer) {
        return customersRepository.save(customer);
    }

    @Override
    public Customers updateCustomer(String id, Customers updatedCustomer) {
        if (customersRepository.existsById(id)) {
            updatedCustomer.setId(id); // Ensure the ID is set for the update
            return customersRepository.save(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    @Override
    public void deleteCustomer(String id) {
        customersRepository.deleteById(id);
    }

}
