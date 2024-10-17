package org.example.advancedrealestate_be.dto;

import lombok.*;
import org.example.advancedrealestate_be.entity.Role;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String userName;
    private String fullName;
    private String password; // Keep this line if you need it for registration purposes
    private Integer status;
    private String email;

    private String roleCode;
    private List<RoleDto> roles; // Add this line to store user roles

    // You can add other fields as needed (e.g., additional information)
}
