package org.example.advancedrealestate_be.config;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrealestate_be.constant.PredefinedRole;
import org.example.advancedrealestate_be.entity.Permission;
import org.example.advancedrealestate_be.entity.Role;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.repository.PermissionRepository;
import org.example.advancedrealestate_be.repository.RoleRepository;
import org.example.advancedrealestate_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    static String ADMIN_PASSWORD = "admin";
    static String ADMIN_EMAIL="admin@gmail.com";
    static Integer ADMIN_STATUS = 1;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                Role role =  roleRepository.save(Role.builder()
                                            .role_name("ADMIN")
                                            .status(1)
                                            .build());
                List<Permission> permissions = Arrays.asList(
                        Permission.builder().permissionName("View admin").link("/admin").build(),
                        Permission.builder().permissionName("View Chat").link("/admin/chat").build(),
                        Permission.builder().permissionName("View Room Chat").link("/admin/room-chat").build(),
                        Permission.builder().permissionName("View User").link("/admin/user").build(),
                        Permission.builder().permissionName("View Building").link("/admin/building").build(),
                        Permission.builder().permissionName("View Service").link("/admin/service").build(),
                        Permission.builder().permissionName("View Map").link("/admin/map").build(),
                        Permission.builder().permissionName("View Action").link("/admin/auction").build(),
                        Permission.builder().permissionName("View Type Building").link("/admin/type-building").build(),
                        Permission.builder().permissionName("View Device").link("/admin/device").build(),
                        Permission.builder().permissionName("View Category").link("/admin/category").build(),
                        Permission.builder().permissionName("View Contract Detail").link("/admin/contract-detail").build()
                );

                // Lưu Permission vào database
                List<Permission> savedPermissions = permissionRepository.saveAll(permissions);

//                // Dùng vòng lặp `for` để liên kết từng Permission với Role
//                for (Permission permission : savedPermissions) {
//                    role.getPermissions().add(permission); // Thêm Permission vào Role
//                }

//                // Lưu Role lại để cập nhật bảng `role_permissions`
//                roleRepository.save(role);

                User user = User.builder()
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .status(ADMIN_STATUS)
                        .role(role)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}