package org.example.advancedrealestate_be.repository;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.advancedrealestate_be.entity.Contracts;
import org.example.advancedrealestate_be.entity.Invoices;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoices,String> {

}
