package org.example.advancedrealestate_be.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.advancedrealestate_be.validator.DobConstraint;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}