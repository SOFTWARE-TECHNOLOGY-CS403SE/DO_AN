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
public class Role {
    @Id
    @Column(unique = true)  // Ensure the name is unique
    String name;

    String description;

    @ManyToMany(mappedBy = "roles")  // Specify the owning side of the relationship
    Set<Permission> permissions;  // Many-to-many relationship
}
