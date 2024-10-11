package org.example.advancedrealestate_be.controller.api.map;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.MapDto;
import org.example.advancedrealestate_be.dto.ServiceDto;
import org.example.advancedrealestate_be.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/admin/")
@Tag(name = "Admin maps", description = "API for map")
@Slf4j
public class MapApiController {

    private final MapService mapService;

    @Autowired
    public MapApiController(MapService mapService) {
        this.mapService = mapService;
    }

    @PostMapping("/maps")
    private ResponseEntity<JSONObject> create(@RequestBody MapDto mapDto) {
        JSONObject data = new JSONObject();
        try {
            MapDto responseDto = mapService.create(mapDto);
            data.put("data", responseDto);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/maps")
    private ResponseEntity<JSONObject> index() {
        JSONObject data = new JSONObject();
        try {
            List<MapDto> mapDtoList = mapService.findAll();
            data.put("total", mapDtoList.size());
            data.put("data", mapDtoList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/maps/{id}")
    private ResponseEntity<JSONObject> detail(@PathVariable String id) {
        JSONObject object = new JSONObject();
        try {
            MapDto responseDto = mapService.findById(id);
            object.put("data", responseDto);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception error) {
            object.put("message", error.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/maps/{id}")
    private ResponseEntity<JSONObject> update(@PathVariable String id, @RequestBody MapDto mapDto) {
        JSONObject data = new JSONObject();
        try {
            MapDto responseDto = mapService.updateById(mapDto ,id);
            data.put("data", responseDto);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/maps/{id}")
    private ResponseEntity<JSONObject> remove(@PathVariable String id) {
        JSONObject data = new JSONObject();
        try {
            MapDto responseDto = mapService.deleteById(id);
            data.put("data", responseDto);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
