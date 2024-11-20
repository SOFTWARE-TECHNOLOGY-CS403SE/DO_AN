package org.example.advancedrealestate_be.controller.api.auth;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.CreateRolePermissionRequest;
import org.example.advancedrealestate_be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/admins/role-permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name="Admin Role Permission")
public class RolePermissionController {
    @Autowired
    RoleService roleService;
    //Crete
    @PostMapping
    public ResponseEntity<JSONObject> addPermissionToRole(@RequestBody CreateRolePermissionRequest request) {
        JSONObject data = new JSONObject();
        try{
            String response =  roleService.createRolePermission(request);
            data.put("status", 200);
            data.put("message", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Delete
}
