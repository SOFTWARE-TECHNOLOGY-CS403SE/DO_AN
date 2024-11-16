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

    String first_name;
    String last_name;
    String user_name;
    String password;
    Integer status;
    String gender;
    String email;
    String phone_number;
    String birthday;
    String avatar;
    String address;
    String hash_reset;
    String id_role;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contracts> contracts;

}