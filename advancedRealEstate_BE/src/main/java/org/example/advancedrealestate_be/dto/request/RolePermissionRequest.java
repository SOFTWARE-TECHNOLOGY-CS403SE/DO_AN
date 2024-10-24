package org.example.advancedrealestate_be.dto.request;

import lombok.*;
import org.example.advancedrealestate_be.entity.Permission;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionRequest {

    Set<Permission> permissions;
}
