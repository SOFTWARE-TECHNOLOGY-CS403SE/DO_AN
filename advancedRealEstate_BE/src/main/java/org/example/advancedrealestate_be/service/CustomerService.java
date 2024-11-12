package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.entity.Customers;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public void deleteCustomer(String id);
    public Customers updateCustomer(String id, Customers updatedCustomer);
    public Customers createCustomer(Customers customer);
    public Optional<Customers> getCustomerById(String id);
    public List<Customers> getAllCustomers();
}
