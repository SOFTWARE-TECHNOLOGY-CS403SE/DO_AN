package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Devices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Devices,String> {
}
