package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.constant.ErrorEnumConstant;
import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.mapper.BuildingMapper;
import org.example.advancedrealestate_be.repository.BuildingRepository;
import org.example.advancedrealestate_be.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingHandler implements BuildingService {

    BuildingRepository buildingRepository;

    @Autowired
    public BuildingHandler(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<BuildingDto> findAll() {

        List<Building> buildingList = buildingRepository.findAll();

        return buildingList.stream().map(BuildingMapper::mapToBuilding).collect(Collectors.toList());
    }

    @Override
    public BuildingDto findById(String id) {
        Optional<Building> building = buildingRepository.findById(id);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        return building.map(value -> new BuildingDto(value.getId(), value.getName(), value.getStructure(), value.getLevel(), value.getArea(), value.getType(), value.getDescription(), value.getNumber_of_basement())).orElse(null);
    }

    @Override
    public BuildingDto create(BuildingDto buildingDto) {
        Building building = new Building();
        building.setName(buildingDto.getName());
        building.setStructure(buildingDto.getStructure());
        building.setLevel(buildingDto.getLevel());
        building.setArea(buildingDto.getArea());
        building.setType(buildingDto.getType());
        building.setDescription(buildingDto.getDescription());
        building.setNumber_of_basement(buildingDto.getNumber_of_basement());

        Building buildingNew = buildingRepository.save(building);
        return new BuildingDto(buildingNew.getId(), buildingNew.getName(), buildingNew.getStructure(), buildingNew.getLevel(), buildingNew.getArea(), buildingNew.getType(), buildingNew.getDescription(), buildingNew.getNumber_of_basement());
    }

    @Override
    public BuildingDto updateById(String id, BuildingDto buildingDto) {
        Optional<Building> building = buildingRepository.findById(id);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        building.get().setName(buildingDto.getName() != null ? buildingDto.getName() : building.get().getName());
        building.get().setStructure(buildingDto.getStructure() != null ? buildingDto.getStructure() : building.get().getStructure());
        building.get().setLevel(buildingDto.getLevel() != null ? buildingDto.getLevel() : building.get().getLevel());
        building.get().setArea(buildingDto.getArea() != null ? buildingDto.getArea() : building.get().getArea());
        building.get().setType(buildingDto.getType() != null ? buildingDto.getType() : building.get().getType());
        building.get().setDescription(buildingDto.getDescription() != null ? buildingDto.getDescription() : building.get().getDescription());
        building.get().setNumber_of_basement(buildingDto.getNumber_of_basement() == 0 ? 0 : buildingDto.getNumber_of_basement());
        building.get().setImage(null);

        Building buildingUpdate = buildingRepository.save(building.get());
        return new BuildingDto(buildingUpdate.getId(), buildingUpdate.getName(), buildingUpdate.getStructure(), buildingUpdate.getLevel(), buildingUpdate.getArea(), buildingUpdate.getType(), buildingUpdate.getDescription(), buildingUpdate.getNumber_of_basement());
    }

    @Override
    public BuildingDto deleteById(String id) {
        Optional<Building> building = buildingRepository.findById(id);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        BuildingDto buildingDto = new BuildingDto(building.get().getId(), building.get().getName(), building.get().getStructure(), building.get().getLevel(), building.get().getArea(), building.get().getType(), building.get().getDescription(), building.get().getNumber_of_basement());
        buildingRepository.delete(building.get());
        return buildingDto;
    }
}