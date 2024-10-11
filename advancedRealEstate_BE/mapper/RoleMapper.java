package org.example.advancedrealestate_be.mapper;



import  org.example.advancedrealestate_be.dto.request.RoleRequest;
import  org.example.advancedrealestate_be.dto.response.RoleResponse;
import  org.example.advancedrealestate_be.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
