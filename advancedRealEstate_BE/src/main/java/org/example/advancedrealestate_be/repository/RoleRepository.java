package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Page<Role> findAll(Pageable pageable);
    List<Role> findAll();
}
