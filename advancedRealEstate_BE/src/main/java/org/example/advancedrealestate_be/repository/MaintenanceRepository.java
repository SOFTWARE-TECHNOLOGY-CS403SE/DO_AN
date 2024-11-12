package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Maintenances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenances,String> {
}
