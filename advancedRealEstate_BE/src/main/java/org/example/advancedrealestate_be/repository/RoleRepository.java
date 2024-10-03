package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Role findOneByCode(String code);
}
