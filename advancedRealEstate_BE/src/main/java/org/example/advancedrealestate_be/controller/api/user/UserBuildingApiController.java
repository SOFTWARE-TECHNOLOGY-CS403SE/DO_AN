package org.example.advancedrealestate_be.controller.api.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User buildings", description = "API for user")
public class UserBuildingApiController {

    BuildingService buildingService;

    @Autowired
    public UserBuildingApiController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }
    @GetMapping("/buildings")
    private ResponseEntity<JSONObject> index(){
        JSONObject data = new JSONObject();
        try{
            List<BuildingDto> buildingDtoList = buildingService.findAll();
            data.put("total" , buildingDtoList.size());
            data.put("data", buildingDtoList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buildings/{id}")
    private ResponseEntity<JSONObject> detail(@PathVariable String id){
        JSONObject object = new JSONObject();
        try{
            BuildingDto buildingDto = buildingService.findById(id);
            object.put("data", buildingDto);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception error){
            object.put("message", error.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
