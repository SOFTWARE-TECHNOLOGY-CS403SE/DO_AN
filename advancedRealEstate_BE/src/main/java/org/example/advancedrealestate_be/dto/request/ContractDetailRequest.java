package org.example.advancedrealestate_be.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractDetailRequest {
    private String description;
    private String note;
    private Double amount;
    private String contractId;  // Assuming contractId is passed to link to an existing contract

}
