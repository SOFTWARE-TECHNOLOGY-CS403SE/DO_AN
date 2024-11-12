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
@Table(name = "customers")
@Getter
@Setter
public class Customers {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String first_name;
    private String last_name;
    private String user_name;
    private String password;
    private int status;
    private String email;
    private String phone_number;
    private DateTime birthday;
    private String avatar;
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)  // Ensure mappedBy matches Contracts field
    private List<Contracts> contracts;


}
