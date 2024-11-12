package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@Table(name = "user")
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

//    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;
    String password;
    String firstName;
    LocalDate dob;
    String lastName;
    String email;
    String gender;
    String phoneNumber;
    String address;
    String birthday;
    boolean isVerify;
    String avatar;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contracts> contracts;


}
