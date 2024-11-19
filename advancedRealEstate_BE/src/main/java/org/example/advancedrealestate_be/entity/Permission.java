package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permissions") // Đổi tên bảng thành số nhiều
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String permissionName;
    String link;

    // Nhiều Permission có thể liên kết với nhiều Role
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles; // Quan hệ nhiều nhiều với Role
}


