package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building,String> {
}
