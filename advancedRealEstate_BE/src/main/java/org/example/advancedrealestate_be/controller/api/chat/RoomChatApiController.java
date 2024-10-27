package org.example.advancedrealestate_be.controller.api.chat;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.RoomChatDto;
import org.example.advancedrealestate_be.dto.request.ApiResponse;
import org.example.advancedrealestate_be.dto.request.PermissionRequest;
import org.example.advancedrealestate_be.dto.response.PermissionResponse;
import org.example.advancedrealestate_be.service.RoomChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/admin/")
@Tag(name = "Admin room chat", description = "API for room chat")
@Slf4j
public class RoomChatApiController {

    private final RoomChatService roomChatService;

    @Autowired
    public RoomChatApiController(RoomChatService roomChatService) {
        this.roomChatService = roomChatService;
    }

    @GetMapping("/room-chats")
    ResponseEntity<JSONObject> index() {
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

    @GetMapping("/room-chats/{id}")
    ResponseEntity<JSONObject> detail(@PathVariable String id) {
        JSONObject data = new JSONObject();
        try {
            data.put("data", roomChatService.findById(id));
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/room-chats")
    ResponseEntity<JSONObject> create(@RequestBody RoomChatDto request) {
        JSONObject data = new JSONObject();
        try {
            data.put("data", roomChatService.create(request));
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/room-chats/{id}/upload-image", consumes = "multipart/form-data")
    ResponseEntity<JSONObject> uploadImage(
            @PathVariable String id,
            @RequestPart("image") @Schema(type = "string", format = "binary")
            MultipartFile imageFile) throws IOException {

        JSONObject data = new JSONObject();
        try {
            data.put("data", roomChatService.uploadImage(id, imageFile));
            data.put("message", "Upload image successfully");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/room-chats/{id}")
    ResponseEntity<JSONObject> updateById(@PathVariable String id ,@RequestBody RoomChatDto request) {
        JSONObject data = new JSONObject();
        try {
            data.put("data", roomChatService.updateById(id ,request));
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/room-chats/{id}")
    ResponseEntity<JSONObject> deleteById(@PathVariable String id ,@RequestBody RoomChatDto request) {
        JSONObject data = new JSONObject();
        try {
            data.put("data", roomChatService.deleteById(id));
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
