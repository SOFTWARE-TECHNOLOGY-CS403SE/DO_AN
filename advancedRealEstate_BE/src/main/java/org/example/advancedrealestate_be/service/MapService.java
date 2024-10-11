package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.MapDto;

import java.util.List;

public interface MapService {

    List<MapDto> findAll();

    MapDto findById(String id);

    MapDto create(MapDto MapDto);

    MapDto updateById(MapDto MapDto, String id);

    MapDto deleteById(String id);
}
