package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,String>  {
}
