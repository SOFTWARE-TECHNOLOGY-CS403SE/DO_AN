package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = true)
    //annotion này giúp gỡ lỗi lặp vô hạn khi mapper
    @JsonBackReference("contract-detail-contracts")
    private Contracts contract;
}
