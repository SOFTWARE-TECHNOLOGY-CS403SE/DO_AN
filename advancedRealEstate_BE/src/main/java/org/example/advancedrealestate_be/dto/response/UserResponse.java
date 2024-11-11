package org.example.advancedrealestate_be.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
    String first_name;
    String last_name;
    String user_name;
    Integer status;
    String email;
    String phone_number;
    LocalDate birthday;
    String avatar;
    String address;
//    String id_role;
    Set<RoleResponse> roles;
}
