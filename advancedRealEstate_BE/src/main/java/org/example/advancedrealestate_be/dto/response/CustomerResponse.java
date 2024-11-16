package org.example.advancedrealestate_be.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.advancedrealestate_be.entity.Contracts;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    private String id;

    private String first_name;
    private String last_name;
    private String user_name;
//    private String password;
    private int status;
    private String email;
    private String phone_number;

    private String avatar;
    private String address;


}
