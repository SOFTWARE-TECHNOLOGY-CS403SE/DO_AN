package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.PasswordDTO;
import org.example.advancedrealestate_be.dto.UserDto;
import org.example.advancedrealestate_be.dto.request.UserCreationRequest;
import org.example.advancedrealestate_be.dto.request.UserUpdateRequest;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    public UserResponse getUser(String id);
    public List<UserResponse> getUsers();
    public void deleteUser(String userId);
    public UserResponse updateUser(String userId, UserUpdateRequest request);
    public UserResponse createUser(UserCreationRequest request);
}
