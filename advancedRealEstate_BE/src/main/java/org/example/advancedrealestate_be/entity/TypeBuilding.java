package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "type_buildings")
public class TypeBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String type_name;
    Double price;
    Integer status;
}
