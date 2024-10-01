package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.advancedrealestate_be.constant.EnumConstant;
import org.example.advancedrealestate_be.constant.EnumEntityConstant;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "buildings")
@Getter
@Setter
public class Building {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String structure;
    private String level;
    private String area;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EnumEntityConstant type;

    private String description;
    private int number_of_basement;

    @Lob
    private byte[] image;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
