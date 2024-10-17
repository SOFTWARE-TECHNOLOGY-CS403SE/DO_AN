package org.example.advancedrealestate_be.dto.request;

import lombok.Getter;

@Getter
public class UpdateInfoUserRequest {

    String firstName;
    String lastName;
    String gender;
    String phoneNumber;
    String address;
    String birthday;
}
