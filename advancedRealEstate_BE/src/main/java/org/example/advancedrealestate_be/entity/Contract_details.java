package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contract_details")
@Getter
@Setter
public class Contract_details {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String description;
    private String note;
    private Double amount;

    // Correct the mappedBy to use the actual field name in Contracts entity
    @OneToMany(mappedBy = "contractDetails", cascade = CascadeType.REMOVE)
    @JsonManagedReference("contract-contract-details")
    private List<Contracts> contractsList = new ArrayList<>();
}

