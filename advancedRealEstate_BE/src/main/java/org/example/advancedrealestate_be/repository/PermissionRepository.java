package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Page<Permission> findAll(Pageable pageable);
    @Query("SELECT p.id FROM Role r " +
            "JOIN r.permissions p " +
            "WHERE r.id = :roleId")
    List<String> findPermissionsByRoleId(@Param("roleId") String roleId);

    @Query("SELECT p.link FROM Role r " +
            "JOIN r.permissions p " +
            "WHERE r.id = :roleId")
    List<String> findPermissionsByRoleLink(@Param("roleId") String roleId);
}
