package org.example.advancedrealestate_be.mapper;

import javax.annotation.Generated;
import org.example.advancedrealestate_be.dto.request.PermissionRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;
import org.example.advancedrealestate_be.entity.Permission;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.v20240919-1711, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionRequest request) {
        if ( request == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.description( request.getDescription() );
        permission.name( request.getName() );

        return permission.build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.description( permission.getDescription() );
        permissionResponse.name( permission.getName() );

        return permissionResponse.build();
    }
}
