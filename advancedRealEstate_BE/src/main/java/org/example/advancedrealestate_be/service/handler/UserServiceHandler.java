package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.service.UserService; // Ensure this is imported

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceHandler implements UserService {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDto register(UserDto userDto) {
//        User user = new User();
//        user.setUserName(userDto.getUserName());
//        user.setFullName(userDto.getFullName());
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user.setStatus(userDto.getStatus());
//
//        userRepository.save(user);
//        return userDto;
//    }
//
//    @Override
//    public UserDto findOneByUserNameAndStatus(String name, int status) {
//        return userRepository.findByUserNameAndStatus(name, status)
//                .map(this::convertToDto)
//                .orElse(null); // Return null or throw an exception if not found
//    }
//
//    @Override
//    public Optional<UserDto> findByUserName(String username) {
//        return userRepository.findByUserName(username).map(this::convertToDto);
//    }
//
//    private UserDto convertToDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setUserName(user.getUserName());
//        userDto.setFullName(user.getFullName());
//        userDto.setRoleCode(user.getRole().getCode());
//        return userDto;
//    }
}
