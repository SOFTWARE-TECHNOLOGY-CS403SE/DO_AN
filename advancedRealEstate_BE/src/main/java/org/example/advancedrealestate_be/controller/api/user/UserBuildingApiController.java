package org.example.advancedrealestate_be.controller.api.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.MapDto;
import org.example.advancedrealestate_be.dto.RoomChatDto;
import org.example.advancedrealestate_be.dto.ServiceDto;
import org.example.advancedrealestate_be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/user")
@Tag(name = "User buildings", description = "API for user")
public class UserBuildingApiController {

    BuildingService buildingService;
    ServiceService serviceService;
    private final MapService mapService;
    private final RoomChatService roomChatService;
    private final AuctionService auctionService;


    @Autowired
    public UserBuildingApiController(BuildingService buildingService, ServiceService serviceService, MapService mapService, RoomChatService roomChatService, AuctionService auctionService) {
        this.buildingService = buildingService;
        this.serviceService = serviceService;
        this.mapService = mapService;
        this.roomChatService = roomChatService;
        this.auctionService = auctionService;
    }

    @GetMapping("/buildings")
    private ResponseEntity<JSONObject> index() {
        JSONObject data = new JSONObject();
        try {
            List<BuildingDto> buildingDtoList = buildingService.findAll();
            data.put("total", buildingDtoList.size());
            data.put("data", buildingDtoList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buildings/{id}")
    private ResponseEntity<JSONObject> detail(@PathVariable String id) {
        JSONObject object = new JSONObject();
        try {
            BuildingDto buildingDto = buildingService.findById(id);
            object.put("data", buildingDto);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception error) {
            object.put("message", error.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/services")
    private ResponseEntity<JSONObject> index_service() {
        JSONObject data = new JSONObject();
        try {
            List<ServiceDto> serviceDtoList = serviceService.findAll();
            data.put("total", serviceDtoList.size());
            data.put("data", serviceDtoList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/services/{id}")
    private ResponseEntity<JSONObject> detail_service(@PathVariable String id) {
        JSONObject object = new JSONObject();
        try {
            ServiceDto responseDto = serviceService.findById(id);
            object.put("data", responseDto);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception error) {
            object.put("message", error.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/maps")
    private ResponseEntity<JSONObject> index_map() {
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
    private ResponseEntity<JSONObject> detail_map(@PathVariable String id) {
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

    @GetMapping("/room-chats")
    ResponseEntity<JSONObject> index_roomChat() {
        JSONObject data = new JSONObject();
        try {
            List<RoomChatDto> roomChatDtoList = roomChatService.findAll();
            data.put("total", roomChatDtoList.size());
            data.put("data", roomChatDtoList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auctions")
    private ResponseEntity<JSONObject> index_auction() {
        JSONObject responseObject = new JSONObject();
        responseObject.put("data", auctionService.findAll());
        responseObject.put("total", auctionService.findAll().size());
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/auctions/{id}")
    private ResponseEntity<JSONObject> detail_auction(@PathVariable String id) {
        JSONObject responseObject = new JSONObject();
        responseObject.put("data", auctionService.findById(id));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
