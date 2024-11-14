package org.example.advancedrealestate_be.dto.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.advancedrealestate_be.entity.Contracts;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {


    private String first_name;
    private String last_name;
    private String user_name;
    private String password;
    private String newPassword;
//    private int status;
    private String email;
    private String phone_number;

    private String avatar;
    private String address;

}
