package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.request.CustomerRequest;
import org.example.advancedrealestate_be.dto.response.CustomerResponse;
import org.example.advancedrealestate_be.entity.Customers;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
  public Customers toEntity(CustomerRequest request, String passwordEncoder){
      Customers customers=new Customers();
      customers.setAddress(request.getAddress());
      customers.setAvatar(request.getAvatar());
      customers.setEmail(request.getEmail());
      customers.setFirst_name(request.getFirst_name());
      customers.setLast_name(request.getLast_name());
      customers.setPassword(passwordEncoder.toString());
      customers.setPhone_number(request.getPhone_number());
      customers.setUser_name(request.getUser_name());
      customers.setStatus(1);

      return customers;
  }
  public CustomerResponse toResponse(Customers entity){
       return CustomerResponse.builder()
               .id(entity.getId())
               .email(entity.getEmail())
               .user_name(entity.getUser_name())
               .build();
  }


    public void updateEntityFromRequest(CustomerRequest request, Customers existingCustomer) {
        existingCustomer.setAddress(request.getAddress());
        existingCustomer.setAvatar(request.getAvatar());
        existingCustomer.setFirst_name(request.getFirst_name());
        existingCustomer.setLast_name(request.getLast_name());
        existingCustomer.setPhone_number(request.getPhone_number());
        existingCustomer.setUser_name(request.getUser_name());
        // Email is not updated here since it's used for validation
    }




}