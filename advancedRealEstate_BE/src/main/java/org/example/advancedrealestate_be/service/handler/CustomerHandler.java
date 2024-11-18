package org.example.advancedrealestate_be.service.handler;


import org.example.advancedrealestate_be.dto.request.CustomerRequest;
import org.example.advancedrealestate_be.dto.response.CustomerResponse;
import org.example.advancedrealestate_be.dto.response.TypeBuildingResponse;
import org.example.advancedrealestate_be.entity.Customers;
import org.example.advancedrealestate_be.entity.TypeBuilding;
import org.example.advancedrealestate_be.mapper.CustomerMapper;
import org.example.advancedrealestate_be.repository.CustomersRepository;
import org.example.advancedrealestate_be.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
public class CustomerHandler implements CustomerService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String deleteCustomer(String id) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        try{
            customersRepository.delete(customer);
            return "Delete successfully !";
        }catch(DataIntegrityViolationException exception){
            throw new RuntimeException(exception);
        }

    }

    @Override
    public String updateCustomer(String id, CustomerRequest request) {
        try{
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
            customersRepository.save(customers);
            return "Update customer successfully !";
        }catch(DataIntegrityViolationException exception){
            throw new RuntimeException("Can not update !");
        }

    }



    @Override
    public String createCustomer(CustomerRequest request) {
        try{
            if(customersRepository.findByUser_nameOrEmail(request.getUser_name(),request.getEmail()).isPresent()){
                throw new RuntimeException("User already exists");
            }
            String encodedPassword =passwordEncoder.encode(request.getPassword());
            Customers newCustomer=customerMapper.toEntity(request,encodedPassword);
            customersRepository.save(newCustomer);
            return "create customer successfully !";
        }catch(DataIntegrityViolationException exception){
            throw new RuntimeException(exception);
        }

    }

    @Override
    public Page<CustomerResponse> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Customers> customersPage = customersRepository.findAll(pageable);

        List<CustomerResponse> customerResponses= customersPage.getContent().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());

        // Tạo đối tượng Page<TypeBuildingResponse> từ List<TypeBuildingResponse> và thông tin phân trang của Page<User>
        return new PageImpl<>(customerResponses, pageable, customersPage.getTotalElements());
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toResponse(customer);
    }

//    @Override
//    public List<CustomerResponse> getAllCustomers() {
//        List<Customers> customers = customersRepository.findAll();
//        return customers.stream()
//                .map(customerMapper::toResponse)
//                .toList();
//    }

    @Override
    public String deleteCustomers(List<String> ids) {
        List<Customers> customers = customersRepository.findAllById(ids);
        try{
            if (customers.size() != ids.size()) {
                throw new RuntimeException("Some customers not found");
            }
            customersRepository.deleteAll(customers);
            return "Can't delete customer successfully !";
        }catch (DataIntegrityViolationException exception){
            throw new RuntimeException(exception);
        }
    }
}
