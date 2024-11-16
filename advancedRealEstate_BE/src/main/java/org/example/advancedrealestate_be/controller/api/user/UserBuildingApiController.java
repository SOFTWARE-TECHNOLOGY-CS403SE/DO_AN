package org.example.advancedrealestate_be.controller.api.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.MapDto;
import org.example.advancedrealestate_be.dto.RoomChatDto;
import org.example.advancedrealestate_be.dto.ServiceDto;
import org.example.advancedrealestate_be.dto.response.AuctionResponse;
import org.example.advancedrealestate_be.dto.response.BuildingResponse;
import org.example.advancedrealestate_be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/user")
@Tag(name = "API for all user", description = "API public")
public class UserBuildingApiController {
    @Autowired
    private final BuildingService buildingService;
    @Autowired
    private final AuctionService auctionService;
    @Autowired
    private final MapService mapService;
    @Autowired
    private final RoomChatService roomChatService;

    @Autowired
    public UserBuildingApiController(BuildingService buildingService, AuctionService auctionService, MapService mapService, RoomChatService roomChatService) {
        this.buildingService = buildingService;
        this.auctionService = auctionService;
        this.mapService = mapService;
        this.roomChatService = roomChatService;
    }

    @GetMapping(value = "/buildings")
    public ResponseEntity<JSONObject> getAllBuildings() {
        JSONObject data = new JSONObject();

        try {
            List<BuildingResponse> allBuilding = buildingService.getAllBuildings();

            data.put("status", 200);
            data.put("data", allBuilding);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buildings/{id}")
    private ResponseEntity<JSONObject> detail(@PathVariable String id) {
        JSONObject object = new JSONObject();
        try {
            List<BuildingResponse> buildingResponse = buildingService.findById(id);
            object.put("data", buildingResponse);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception error) {
            object.put("message", error.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/maps")
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

    @GetMapping(value = "/maps/{id}")
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

    @GetMapping(value = "/room-chats")
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

//    @GetMapping("/services")
//    private ResponseEntity<JSONObject> index_service() {
//        JSONObject data = new JSONObject();
//        try {
//            List<ServiceDto> serviceDtoList = serviceService.findAll();
//            data.put("total", serviceDtoList.size());
//            data.put("data", serviceDtoList);
//            return new ResponseEntity<>(data, HttpStatus.OK);
//        } catch (Exception error) {
//            data.put("message", error.getMessage());
//            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/services/{id}")
//    private ResponseEntity<JSONObject> detail_service(@PathVariable String id) {
//        JSONObject object = new JSONObject();
//        try {
//            ServiceDto responseDto = serviceService.findById(id);
//            object.put("data", responseDto);
//            return new ResponseEntity<>(object, HttpStatus.OK);
//        } catch (Exception error) {
//            object.put("message", error.getMessage());
//            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
