package org.example.advancedrealestate_be.controller.api.device;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.DeviceRequest;
import org.example.advancedrealestate_be.dto.response.DeviceResponse;
import org.example.advancedrealestate_be.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            data.put("data", response);
            data.put("message", "Device created successfully !");
            return new ResponseEntity<>(data, HttpStatus.OK);

        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getDeviceById(@PathVariable String id){
           JSONObject data=new JSONObject();
           try{
               DeviceResponse response=deviceService.getDeviceById(id);
               data.put("data",response);
               return new ResponseEntity<>(data,HttpStatus.OK);
           }catch(Exception e){
               data.put("message",e.getMessage());
               return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
           }
    }

    @GetMapping
    public ResponseEntity<JSONObject> getAllDevices() {
        JSONObject data = new JSONObject();
        try{
             List<DeviceResponse> deviceResponseList=deviceService.getAllDevice();
             data.put("total",deviceResponseList.size());
             data.put("data",deviceResponseList);
             return new ResponseEntity<>(data,HttpStatus.OK);

        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("{id}")
    public ResponseEntity<JSONObject> updateDevice(@PathVariable String id, @RequestBody DeviceRequest request){
        JSONObject data=new JSONObject();
        try{
            DeviceResponse response=deviceService.updateDevice(id,request);
            data.put("data",response);
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
            data.put("message","Device was deleted successfully");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
