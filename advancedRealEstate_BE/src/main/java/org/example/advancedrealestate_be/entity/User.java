package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String first_name;
    String last_name;
    String user_name;
    String password;
    Integer status;
    String email;
    String phone_number;
    LocalDate birthday;
    @Lob
    String avatar;
    String address;
    String hash_reset;
    String id_role;

    @ManyToMany
    Set<Role> roles;
}
