package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoices {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private LocalDate invoice_date; // Automatically generated date

    private LocalDate expire_date_payment;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contracts contracts;
}
