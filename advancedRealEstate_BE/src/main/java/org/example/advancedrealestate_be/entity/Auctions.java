package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auctions")
@Getter
@Setter
public class Auctions {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private DateTime start_time;
    private DateTime end_time;
    private DateTime start_date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;  // Renamed from buildings to building

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Auctions_items> auctions_items;


}
