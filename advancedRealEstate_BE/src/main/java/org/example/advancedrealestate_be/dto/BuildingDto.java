package org.example.advancedrealestate_be.dto;


import lombok.*;
import org.example.advancedrealestate_be.constant.EnumConstant;
import org.example.advancedrealestate_be.constant.EnumEntityConstant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuildingDto {

    private String id;
    private String name;
    private String structure;
    private String level;
    private String area;
    private EnumEntityConstant type;
    private String description;
    private int number_of_basement;
    private byte[] image;
    private String file_type;
}
