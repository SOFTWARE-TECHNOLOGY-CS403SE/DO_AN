package org.example.advancedrealestate_be.dto.response;

import lombok.*;
import org.example.advancedrealestate_be.entity.Permission;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponse {

    String roleName;
    String description;
    Set<Permission> permissions;
    Set<Permission> oldPermissions;

}
