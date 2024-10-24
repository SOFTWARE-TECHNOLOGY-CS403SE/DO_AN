package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.*;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import org.example.advancedrealestate_be.dto.response.UserRoleResponse;

import java.util.List;

public interface UserService {
    UserResponse getMyInfo();
    UserResponse getMyInfo(String email);
    UserResponse getUser(String id);
    List<UserResponse> getUsers();
    void deleteUser(String userId);
    UserResponse updateUser(String userId, UserUpdateRequest request);
    UserResponse updateUserInfo(String userId, UpdateInfoUserRequest request);
    UserRoleResponse updateRoleUser(String userId, UserRoleRequest request);
    UserResponse createUser(UserCreationRequest request);
}
