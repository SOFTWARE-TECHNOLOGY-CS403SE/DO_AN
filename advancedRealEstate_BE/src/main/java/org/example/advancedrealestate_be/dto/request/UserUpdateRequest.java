package org.example.advancedrealestate_be.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import org.example.advancedrealestate_be.validator.DobConstraint;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.example.advancedrealestate_be.repository.RoleRepository;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String user_name;
    String first_name;
    String last_name;
    Integer status;
    String phone_number;
    LocalDate birthday;
//    String avatar;
    String address;
//    String id_role;
    @NotNull
    private String email;

//    private Set<RoleRepository> roles;

}
