package org.example.advancedrealestate_be.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    String email;
    String gender;
    String phoneNumber;
    String address;
    String birthday;
    boolean isVerify;
    String avatar;
    Set<RoleResponse> roles;
}
