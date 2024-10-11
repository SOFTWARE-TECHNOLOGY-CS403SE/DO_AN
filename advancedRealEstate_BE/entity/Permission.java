package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Permission {
    @Id
    @Column(unique = true)  // Ensure the name is unique
    String name;

    String description;

    @ManyToMany
    Set<Role> roles;  // Create a many-to-many relationship back to roles
}
