package org.example.advancedrealestate_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "building")
@Getter
@Setter
public class Building {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String structure;
    private String area;

    @Column(name = "type")
    private String type;
    private String status;

    @Column(columnDefinition = "text")
    private String description;
    private int number_of_basement;
    private double price;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private String file_type;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Auctions> auctions;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Maintenances> maintenances;

    @ManyToOne
    @JoinColumn(name = "building_type_id")
    private Type_buildings type_buildings;

    @ManyToOne
    @JoinColumn(name = "map_id", nullable = true)
    //annotion này giúp gỡ lỗi lặp vô hạn khi mapper
    @JsonBackReference("building-maps")
    private Map map;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Devices> devices;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Contracts> contracts;
}
