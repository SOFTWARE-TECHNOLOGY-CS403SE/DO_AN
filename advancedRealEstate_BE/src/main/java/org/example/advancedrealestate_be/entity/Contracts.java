package org.example.advancedrealestate_be.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contracts")
@Getter
@Setter
public class Contracts {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String contract_name;
    private String contract_details;

    @ManyToOne
    @JoinColumn(name = "customer_id")  // Foreign key linking to Customers
    private Customers customer;


    @ManyToOne
    @JoinColumn(name = "building_id")  // Foreign key linking to Customers
    private Building building;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "contracts", cascade = CascadeType.ALL)
    private List<Transactions> transactions;

    @OneToMany(mappedBy = "contracts", cascade = CascadeType.ALL)
    private List<Invoices> invoices;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
    @JsonManagedReference("contract-contract-details")
    private List<Contract_details> contract_detailsList = new ArrayList<>();
}
