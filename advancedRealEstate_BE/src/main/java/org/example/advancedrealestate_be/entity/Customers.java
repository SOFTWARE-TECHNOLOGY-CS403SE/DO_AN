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

    private String first_name;   // OK, but consider camelCase: firstName
    private String last_name;    // OK, but consider camelCase: lastName
    private String user_name;    // OK, but consider camelCase: userName
    private String password;
    private int status;          // Consider changing to `boolean` if it stores a binary state
    private String email;
    private String phone_number; // OK, but consider camelCase: phoneNumber

    private String avatar;
    private String address;
    private String hash;
    private int is_activity;     // Consider changing to `boolean` if it stores a binary state

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)  // Ensure `mappedBy` is correct
    private List<Contracts> contracts;
}
