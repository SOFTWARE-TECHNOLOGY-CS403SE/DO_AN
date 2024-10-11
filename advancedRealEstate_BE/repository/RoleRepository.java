package org.example.advancedrealestate_be.repository;


import org.example.advancedrealestate_be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
