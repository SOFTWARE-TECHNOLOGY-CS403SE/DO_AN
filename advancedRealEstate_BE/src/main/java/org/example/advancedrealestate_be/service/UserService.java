package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.PasswordDTO;
import org.example.advancedrealestate_be.dto.UserDto;
import org.example.advancedrealestate_be.dto.request.UpdateInfoUserRequest;
import org.example.advancedrealestate_be.dto.request.UserCreationRequest;
import org.example.advancedrealestate_be.dto.request.UserUpdateRequest;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
     UserResponse getMyInfo();
     UserResponse getUser(String id);
    List<UserResponse> getUsers();
    void deleteUser(String userId);
    UserResponse updateUser(String userId, UserUpdateRequest request);
    UserResponse updateUserInfo(String userId, UpdateInfoUserRequest request);
    UserResponse createUser(UserCreationRequest request);
}
