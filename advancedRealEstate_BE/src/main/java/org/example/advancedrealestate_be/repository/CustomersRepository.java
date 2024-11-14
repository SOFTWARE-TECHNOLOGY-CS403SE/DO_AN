package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, String> {
    @Query("SELECT c FROM Customers c WHERE c.user_name = :user_name OR c.email = :email")
    Optional<Customers> findByUser_nameOrEmail(@Param("user_name") String user_name, @Param("email") String email);

}
