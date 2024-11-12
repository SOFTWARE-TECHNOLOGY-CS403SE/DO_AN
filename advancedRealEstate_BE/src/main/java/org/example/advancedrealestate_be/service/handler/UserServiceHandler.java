package org.example.advancedrealestate_be.service.handler;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrealestate_be.constant.PredefinedRole;
import org.example.advancedrealestate_be.dto.request.*;
import org.example.advancedrealestate_be.dto.response.UserResponse;
import org.example.advancedrealestate_be.dto.response.UserRoleResponse;
import org.example.advancedrealestate_be.entity.Role;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.UserMapper;
import org.example.advancedrealestate_be.repository.RoleRepository;
import org.example.advancedrealestate_be.repository.UserRepository;
import org.example.advancedrealestate_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceHandler implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceHandler(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponse createUser(UserCreationRequest request) {

        User user = userMapper.toUser(request);

//        User existUser = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Optional<User> existUser = userRepository.findByEmail(request.getEmail());
        if(existUser.isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'STAFF')")
    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("returnObject.email == authentication.email")
//    public UserResponse updateUser(String userId, UserUpdateRequest request) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        userMapper.updateUser(user, request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        var roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));
//
//        return userMapper.toUserResponse(userRepository.save(user));
//    }



    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);


        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }


        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'STAFF')")
    @Override
    public UserResponse updateUserInfo(String userId, UpdateInfoUserRequest request) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setUsername(request.getFirstName() +" "+ request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setBirthday(request.getBirthday());

        User userUpdate = userRepository.save(user);
        return new UserResponse(
            userUpdate.getId(),
            userUpdate.getUsername(),
                userUpdate.getFirstName(),
                userUpdate.getLastName(),
                userUpdate.getDob(),
                userUpdate.getEmail(),
                userUpdate.getGender(),
                userUpdate.getPhoneNumber(),
                userUpdate.getAddress(),
                userUpdate.getBirthday(),
                userUpdate.isVerify(),
                userUpdate.getAvatar(),
                null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserRoleResponse updateRoleUser(String userId, UserRoleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Set<UserRoleResponse.Role> oldRoles = user.getRoles().stream()
                .map(role -> new UserRoleResponse.Role(role.getName(),
                        role.getPermissions().stream()
                                .map(permission -> new UserRoleResponse.Permission(permission.getName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toSet());
        List<UserRoleRequest.Role> roleNames = request.getRoles();
        Set<Role> roles = roleNames.stream()
            .map(roleReq -> roleRepository.findById(roleReq.getName())
              .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
            .collect(Collectors.toSet());
        user.setRoles(roles);
        User userRoleUpdate = userRepository.save(user);

        Set<UserRoleResponse.Role> responseRoles = roles.stream()
            .map(role -> new UserRoleResponse.Role(role.getName(),
                 role.getPermissions().stream()
            .map(permission -> new UserRoleResponse.Permission(permission.getName()))
        .collect(Collectors.toList())))
        .collect(Collectors.toSet());

        return new UserRoleResponse(userRoleUpdate.getId(), userRoleUpdate.getEmail(), responseRoles, oldRoles);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
//ko cần đâu, tạo q
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }


}
