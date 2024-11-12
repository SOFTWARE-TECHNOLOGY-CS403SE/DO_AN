package org.example.advancedrealestate_be.controller.api.auth;
import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.example.advancedrealestate_be.dto.request.*;
import org.example.advancedrealestate_be.dto.response.ApiResponse;
import org.example.advancedrealestate_be.dto.response.UserRoleResponse;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    ApiResponse<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        // Lấy dữ liệu người dùng với phân trang
        Page<UserResponse> pageResult = userService.getUsers(page, size);

        // Tạo đối tượng response chứa thông tin phân trang và danh sách người dùng
        Map<String, Object> response = new HashMap<>();

        // Metadata về phân trang
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("total", pageResult.getTotalElements());
        pagination.put("per_page", pageResult.getSize());
        pagination.put("current_page", pageResult.getNumber() + 1);
        pagination.put("last_page", pageResult.getTotalPages());
        pagination.put("from", (pageResult.getNumber() * pageResult.getSize()) + 1);
        pagination.put("to", Math.min((pageResult.getNumber() + 1) * pageResult.getSize(), pageResult.getTotalElements()));

        response.put("pagination", pagination);
        response.put("data", pageResult.getContent());

        // Trả về đối tượng ApiResponse chứa phân trang và dữ liệu
        return ApiResponse.<Map<String, Object>>builder()
                .result(response)
                .build();
    }

    @PostMapping(consumes = "multipart/form-data")
    ApiResponse<UserResponse> createUser(@ModelAttribute @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message(userService.createUser(request))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        System.out.println("ID User: " + userId);
        System.out.println("Request: " + request);
        return ApiResponse.<UserResponse>builder()
                .message(userService.updateUser(userId, request))
                .build();
    }

    @PutMapping("/reset-password/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdatePasswordRequest request) {
        System.out.println("ID User: " + userId);
        System.out.println("Request: " + request);
        return ApiResponse.<UserResponse>builder()
                .message(userService.updatePasswordUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

//    @PatchMapping("/{userId}")
//    ApiResponse<UserResponse> updateUserInfo(@PathVariable String userId, @RequestBody UpdateInfoUserRequest request) {
//
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.updateUserInfo(userId, request))
//                .build();
//    }

//    @PatchMapping("role-user/{userId}")
//    ApiResponse<UserRoleResponse> updateRoleUser(@PathVariable String userId, @RequestBody UserRoleRequest request) {
//        UserRoleResponse userRoleResponse = userService.updateRoleUser(userId, request);
//        return ApiResponse.<UserRoleResponse>builder()
//                .result(userRoleResponse)
//                .build();
//    }
}
