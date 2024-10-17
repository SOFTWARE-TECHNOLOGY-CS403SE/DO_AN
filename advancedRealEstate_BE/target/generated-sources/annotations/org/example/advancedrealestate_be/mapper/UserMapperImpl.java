package org.example.advancedrealestate_be.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.example.advancedrealestate_be.dto.request.UserCreationRequest;
import org.example.advancedrealestate_be.dto.request.UserUpdateRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;
import org.example.advancedrealestate_be.dto.response.RoleResponse;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import org.example.advancedrealestate_be.entity.Permission;
import org.example.advancedrealestate_be.entity.Role;
import org.example.advancedrealestate_be.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.v20240919-1711, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.dob( request.getDob() );
        user.email( request.getEmail() );
        user.firstName( request.getFirstName() );
        user.lastName( request.getLastName() );
        user.password( request.getPassword() );
        user.username( request.getUsername() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.address( user.getAddress() );
        userResponse.avatar( user.getAvatar() );
        userResponse.birthday( user.getBirthday() );
        userResponse.dob( user.getDob() );
        userResponse.email( user.getEmail() );
        userResponse.firstName( user.getFirstName() );
        userResponse.gender( user.getGender() );
        userResponse.id( user.getId() );
        userResponse.lastName( user.getLastName() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.roles( roleSetToRoleResponseSet( user.getRoles() ) );
        userResponse.username( user.getUsername() );

        return userResponse.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setAddress( request.getAddress() );
        user.setAvatar( request.getAvatar() );
        user.setBirthday( request.getBirthday() );
        user.setDob( request.getDob() );
        user.setEmail( request.getEmail() );
        user.setFirstName( request.getFirstName() );
        user.setGender( request.getGender() );
        user.setLastName( request.getLastName() );
        user.setPassword( request.getPassword() );
        user.setPhoneNumber( request.getPhoneNumber() );
        user.setUsername( request.getUsername() );
        user.setVerify( request.isVerify() );
    }

    protected PermissionResponse permissionToPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.description( permission.getDescription() );
        permissionResponse.name( permission.getName() );

        return permissionResponse.build();
    }

    protected Set<PermissionResponse> permissionSetToPermissionResponseSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionResponse> set1 = new LinkedHashSet<PermissionResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionToPermissionResponse( permission ) );
        }

        return set1;
    }

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.description( role.getDescription() );
        roleResponse.name( role.getName() );
        roleResponse.permissions( permissionSetToPermissionResponseSet( role.getPermissions() ) );

        return roleResponse.build();
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }
}
