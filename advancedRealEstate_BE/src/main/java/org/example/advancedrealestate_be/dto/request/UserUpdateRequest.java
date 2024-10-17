package org.example.advancedrealestate_be.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.advancedrealestate_be.validator.DobConstraint;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
   String username;
    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String address;
    String birthday;
    boolean isVerify;
    String avatar;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    @NotNull
    private String email;

    @Size(min = 6)
    private String password;

    private Set<String> roles;


}
