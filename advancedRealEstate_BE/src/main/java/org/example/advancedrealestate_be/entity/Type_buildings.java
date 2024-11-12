package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "type_buildings")
@Getter
@Setter
public class Type_buildings {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String type_name;
    private Double price;
    private int status;
    @OneToMany(mappedBy = "type_buildings", cascade = CascadeType.ALL)
    private List<Building> building;

}
