package org.example.advancedrealestate_be.controller.api.device;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.DeviceRequest;
import org.example.advancedrealestate_be.dto.response.CategoryResponse;
import org.example.advancedrealestate_be.dto.response.DeviceResponse;
import org.example.advancedrealestate_be.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name="bearerAuth")
@RequestMapping("/api/device")
@Tag(name="User device", description = " API for user")
public class DeviceApiController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<JSONObject> createDevice(@RequestBody DeviceRequest request){
        JSONObject data=new JSONObject();
        try{
            DeviceResponse response=deviceService.createDevice(request);
            data.put("status", 200);
            data.put("message", "Device created successfully !");
            return new ResponseEntity<>(data, HttpStatus.OK);

        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<JSONObject> getDeviceById(@PathVariable String id){
//           JSONObject data=new JSONObject();
//           try{
//               DeviceResponse response=deviceService.getDeviceById(id);
//               data.put("data",response);
//               return new ResponseEntity<>(data,HttpStatus.OK);
//           }catch(Exception e){
//               data.put("message",e.getMessage());
//               return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
//           }
//    }

    @GetMapping
    public ResponseEntity<JSONObject> getAllDevices(
                                                    @RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size) {

        JSONObject data = new JSONObject();
        Map<String, Object> response = new HashMap<>();

        try {
            if (page == null || size == null) {
                List<DeviceResponse> categories = deviceService.getAllDevice();

                response.put("data", categories);
            } else {
                Page<DeviceResponse> pageResult = deviceService.getDevice(page, size);

                Map<String, Object> pagination = new HashMap<>();
                pagination.put("total", pageResult.getTotalElements());
                pagination.put("per_page", pageResult.getSize());
                pagination.put("current_page", pageResult.getNumber() + 1);
                pagination.put("last_page", pageResult.getTotalPages());
                pagination.put("from", (pageResult.getNumber() * pageResult.getSize()) + 1);
                pagination.put("to", Math.min((pageResult.getNumber() + 1) * pageResult.getSize(), pageResult.getTotalElements()));

                response.put("pagination", pagination);
                response.put("data", pageResult.getContent());
            }

            data.put("status", 200);
            data.put("data", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<JSONObject> updateDevice(@PathVariable String id, @RequestBody DeviceRequest request){
        JSONObject data=new JSONObject();
        try{
            deviceService.updateDevice(id,request);

            data.put("status",200);
            data.put("message","device updated successfully");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch(Exception e){
            data.put("message",e.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteDevice(@PathVariable String id){
        JSONObject data=new JSONObject();
        try{
            deviceService.deleteDevice(id);
            data.put("status", 200);
            data.put("message","Device was deleted successfully");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
