package org.example.advancedrealestate_be.service.handler;
import org.example.advancedrealestate_be.constant.ErrorEnumConstant;
import org.example.advancedrealestate_be.dto.MapDto;
import org.example.advancedrealestate_be.entity.Map;
import org.example.advancedrealestate_be.mapper.MapMapper;
import org.example.advancedrealestate_be.repository.MapRepository;
import org.example.advancedrealestate_be.service.MapService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MapHandler implements MapService {


    private final ModelMapper modelMapper;
    private final MapRepository mapRepository;

    @Autowired
    public MapHandler(ModelMapper modelMapper, MapRepository mapRepository) {
        this.modelMapper = modelMapper;
        this.mapRepository = mapRepository;
    }


    @Override
    public List<MapDto> findAll() {

        List<Map> mapList = mapRepository.findAll();

        return mapList.stream()
                .map(MapMapper::mapToMap)
                .collect(Collectors.toList());
    }


    @Override
    public MapDto findById(String id) {
        Optional<Map> map = mapRepository.findById(id);
        if (map.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.MapNotFound.toString());
        }
        return map.map(
                value -> new MapDto(
                    value.getId(),
                    value.getMap_name(),
                    value.getLatitude(),
                    value.getLongitude(),
                    value.getAddress(),
                    value.getProvince(),
                    value.getDistrict(),
                    value.getWard()
                ))
        .orElse(null);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public MapDto create(MapDto MapDto) {
        Map map = modelMapper.map(MapDto, Map.class);
        Map mapNew = mapRepository.save(map);

        return new MapDto(
                mapNew.getId(),
                mapNew.getMap_name(),
                mapNew.getLatitude(),
                mapNew.getLongitude(),
                mapNew.getAddress(),
                mapNew.getProvince(),
                mapNew.getDistrict(),
                mapNew.getWard()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Override
    public MapDto updateById(MapDto mapDto, String id) {
        Optional<Map> map = mapRepository.findById(id);
        if (map.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.MapNotFound.toString());
        }
        map.get().setMap_name(mapDto.getMap_name() != null ? mapDto.getMap_name() : map.get().getMap_name());
        map.get().setLatitude(mapDto.getLatitude() != null ? mapDto.getLatitude() : map.get().getLatitude());
        map.get().setLongitude(mapDto.getLongitude() != null ? mapDto.getLongitude() : map.get().getLongitude());
        map.get().setAddress(mapDto.getAddress() != null ? mapDto.getAddress() : map.get().getAddress());
        map.get().setProvince(mapDto.getProvince() != null ? mapDto.getProvince() : map.get().getProvince());
        map.get().setDistrict(mapDto.getDistrict() != null ? mapDto.getDistrict() : map.get().getDistrict());
        map.get().setWard(mapDto.getWard() != null ? mapDto.getWard() : map.get().getWard());

        Map mapUpdate = mapRepository.save(map.get());
        return new MapDto(
                mapUpdate.getId(),
                mapUpdate.getMap_name(),
                mapUpdate.getLatitude(),
                mapUpdate.getLongitude(),
                mapUpdate.getAddress(),
                mapUpdate.getProvince(),
                mapUpdate.getDistrict(),
                mapUpdate.getWard());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Override
    public MapDto deleteById(String id) {
        Optional<Map> map = mapRepository.findById(id);
        if (map.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorEnumConstant.MapNotFound.toString());
        }
        MapDto mapDto = new MapDto(
                map.get().getId(),
                map.get().getMap_name(),
                map.get().getLatitude(),
                map.get().getLongitude(),
                map.get().getAddress(),
                map.get().getProvince(),
                map.get().getDistrict(),
                map.get().getWard());
        mapRepository.delete(map.get());
        return mapDto;
    }
}
