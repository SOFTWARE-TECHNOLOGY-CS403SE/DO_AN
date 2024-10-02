package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.entity.Building;

import java.util.List;

public interface BuildingService {

    List<BuildingDto> findAll();

    BuildingDto findById(String id);

    BuildingDto create(BuildingDto buildingDto);

    BuildingDto updateById( BuildingDto buildingDto);

    BuildingDto deleteById(String id);
}
