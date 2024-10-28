package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.constant.ErrorEnumConstant;
import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.RoomChatDto;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.RoomChat;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.BuildingMapper;
import org.example.advancedrealestate_be.repository.BuildingRepository;
import org.example.advancedrealestate_be.service.BuildingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingHandler implements BuildingService {


    private final BuildingRepository buildingRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public BuildingHandler(BuildingRepository buildingRepository, ModelMapper modelMapper) {
        this.buildingRepository = buildingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BuildingDto> findAll() {

        List<Building> buildingList = buildingRepository.findAll();

        return buildingList.stream().map(BuildingMapper::mapToBuilding).collect(Collectors.toList());
    }

    @Override
    public BuildingDto findById(String id) {
        Optional<Building> building = buildingRepository.findById(id);
        if (building.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        return building.map(value -> new BuildingDto(
                value.getId(), value.getName(),
                value.getStructure(), value.getLevel(),
                value.getArea(), value.getType(),
                value.getDescription(),
                value.getNumber_of_basement(),
                value.getImage(),
                value.getFile_type()))
        .orElse(null);
    }

//    @Override
//    public BuildingDto create(BuildingDto buildingDto) {
//        Building building = new Building();
//        building.setName(buildingDto.getName());
//        building.setStructure(buildingDto.getStructure());
//        building.setLevel(buildingDto.getLevel());
//        building.setArea(buildingDto.getArea());
//        building.setType(buildingDto.getType());
//        building.setDescription(buildingDto.getDescription());
//        building.setNumber_of_basement(buildingDto.getNumber_of_basement());
//
//        Building buildingNew = buildingRepository.save(building);
//        return new BuildingDto(buildingNew.getId(), buildingNew.getName(), buildingNew.getStructure(), buildingNew.getLevel(), buildingNew.getArea(), buildingNew.getType(), buildingNew.getDescription(), buildingNew.getNumber_of_basement());
//    }

    @Transactional
    @Override
    public BuildingDto create(BuildingDto buildingDto) {
        Building buildingEntity = modelMapper.map(buildingDto, Building.class);
        Building buildingNew = buildingRepository.save(buildingEntity);
        return new BuildingDto(
                buildingNew.getId(),
                buildingNew.getName(),
                buildingNew.getStructure(),
                buildingNew.getLevel(),
                buildingNew.getArea(),
                buildingNew.getType(),
                buildingNew.getDescription(),
                buildingNew.getNumber_of_basement(),
                null,
                null
        );
    }

    @Override
    public BuildingDto updateById(BuildingDto buildingDto, String id) {
        Optional<Building> building = buildingRepository.findById(id);
        if (building.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        building.get().setName(buildingDto.getName() != null ? buildingDto.getName() : building.get().getName());
        building.get().setStructure(buildingDto.getStructure() != null ? buildingDto.getStructure() : building.get().getStructure());
        building.get().setLevel(buildingDto.getLevel() != null ? buildingDto.getLevel() : building.get().getLevel());
        building.get().setArea(buildingDto.getArea() != null ? buildingDto.getArea() : building.get().getArea());
        building.get().setType(buildingDto.getType() != null ? buildingDto.getType() : building.get().getType());
        building.get().setDescription(buildingDto.getDescription() != null ? buildingDto.getDescription() : building.get().getDescription());
        building.get().setNumber_of_basement(buildingDto.getNumber_of_basement() == 0 ? 0 : buildingDto.getNumber_of_basement());
        Building buildingUpdate = buildingRepository.save(building.get());
        return new BuildingDto(buildingUpdate.getId(),
                buildingUpdate.getName(), buildingUpdate.getStructure(),
                buildingUpdate.getLevel(), buildingUpdate.getArea(),
                buildingUpdate.getType(), buildingUpdate.getDescription(),
                buildingUpdate.getNumber_of_basement(),
                null,
                buildingDto.getFile_type()
        );
    }

    @Override
    public BuildingDto deleteById(String id) {
        Optional<Building> building = buildingRepository.findById(id);
        if (building.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.BuildingNotFound.toString());
        }
        BuildingDto buildingDto = new BuildingDto(
                building.get().getId(),
                building.get().getName(),
                building.get().getStructure(), building.get().getLevel(),
                building.get().getArea(), building.get().getType(),
                building.get().getDescription(),
                building.get().getNumber_of_basement(),
                null,
                building.get().getFile_type()
        );
        buildingRepository.delete(building.get());
        return buildingDto;
    }

    @Override
    public BuildingDto uploadImage(String id, MultipartFile imageFile) throws IOException {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));
        building.setImage(imageFile.getBytes());
        building.setFile_type(imageFile.getContentType());
        Building buildingUpLoad = buildingRepository.save(building);
        return new BuildingDto(
                buildingUpLoad.getId(),
                buildingUpLoad.getName(),
                null,
                null,null,
                buildingUpLoad.getType(),
                null,
                buildingUpLoad.getNumber_of_basement(),
                null,
                null
        );
    }
}
