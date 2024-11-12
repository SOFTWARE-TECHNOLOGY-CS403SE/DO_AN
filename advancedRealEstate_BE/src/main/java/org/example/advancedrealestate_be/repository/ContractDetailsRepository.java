package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Contract_details;
import org.example.advancedrealestate_be.entity.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDetailsRepository extends JpaRepository<Contract_details,String> {
}
