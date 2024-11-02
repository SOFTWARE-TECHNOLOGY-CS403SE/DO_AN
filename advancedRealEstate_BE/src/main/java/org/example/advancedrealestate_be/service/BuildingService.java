package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.RoomChatDto;
import org.example.advancedrealestate_be.dto.request.CreateBuildingRequest;
import org.example.advancedrealestate_be.entity.Building;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BuildingService {

    List<BuildingDto> findAll();

    BuildingDto findById(String id);

    BuildingDto create(CreateBuildingRequest buildingRequestDto);

    BuildingDto updateById( BuildingDto buildingDto, String id);

    BuildingDto deleteById(String id);

    BuildingDto uploadImage(String id, MultipartFile imageFile) throws IOException;

}
