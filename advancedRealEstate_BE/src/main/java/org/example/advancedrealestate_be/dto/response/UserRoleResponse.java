package org.example.advancedrealestate_be.dto.response;


import lombok.*;
import org.example.advancedrealestate_be.converter.RoleConverter;
import org.example.advancedrealestate_be.entity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserRoleResponse {

    private String id;
    private String email;
    private Set<Role> roles;
    private Set<Role> oldRoles;


    public UserRoleResponse(String id, String email, Set<Role> responseRoles, Set<Role> oldRoles) {
        this.id = id;
        this.email = email;
        this.roles = RoleConverter.convertToDto(responseRoles);
        this.oldRoles = RoleConverter.convertToDto(oldRoles);
    }

    @Data
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {
        private String name;
        private List<Permission> permissions;
    }

    @Data
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Permission {
        private String name;
    }
}

