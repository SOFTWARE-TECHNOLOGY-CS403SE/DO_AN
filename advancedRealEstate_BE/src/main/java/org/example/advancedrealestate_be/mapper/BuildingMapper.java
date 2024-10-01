package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.entity.Building;

import java.util.Optional;

public class BuildingMapper {

    public static BuildingDto mapToBuilding(Building building) {

        BuildingDto buildingDto = BuildingDto.builder()
                .id(building.getId())
                .name(building.getName())
                .structure(building.getStructure())
                .level(building.getLevel())
                .area(building.getArea())
                .type(building.getType())
                .description(building.getDescription())
                .number_of_basement(building.getNumber_of_basement())
                .build();
        if (buildingDto != null) {

            return buildingDto;

        }else{

            System.out.println(Optional.empty());

            return null;
        }
    }
}
