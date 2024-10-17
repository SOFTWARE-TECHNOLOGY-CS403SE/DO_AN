package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.request.UserCreationRequest;
import org.example.advancedrealestate_be.dto.request.UserUpdateRequest;
import org.example.advancedrealestate_be.dto.response.RoleResponse;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import org.example.advancedrealestate_be.entity.Role;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.mapper.UserMapper;
import org.example.advancedrealestate_be.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public User toUser(UserCreationRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Note: Password should be encoded later
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        // Add additional fields as necessary
        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null; // Return null if the user is null
        }

        Set<RoleResponse> roleResponses = user.getRoles().stream()
                .map(this::toRoleResponse) // Convert Role to RoleResponse
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob())
                .roles(roleResponses) // Use the converted roles
                .isVerify(user.isVerify()) // Assuming there's an isVerify method
                .build();
    }

    private RoleResponse toRoleResponse(Role role) {
        if (role == null) {
            return null; // Return null if the role is null
        }

        return RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
//                .permissions(role.getPermissions()) // Assuming permissions is properly mapped
                .build();
    }
    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        // Check if user or request is null to prevent NullPointerException
        if (user == null || request == null) {
            return; // You might want to throw an exception or log this
        }

        // Update fields based on request
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        // Consider renaming `dob` to `birthday` for clarity
        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }

        // Update the password if provided; ensure it's encoded in the service layer
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);
        }

        // Handle roles update if provided
        if (request.getRoles() != null) {
            // Assuming roles is a List or Set of role IDs
            Set<Role> roles = new HashSet<>();
            for (String roleId : request.getRoles()) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
            user.setRoles(roles);
        }

        // Add any additional fields you need to update
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        if (request.isVerify() != false) {
            user.setVerify(request.isVerify());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

//        // Consider adding a timestamp for updates (e.g., lastModified)
//        user.setLastModified(LocalDateTime.now());
    }

}
