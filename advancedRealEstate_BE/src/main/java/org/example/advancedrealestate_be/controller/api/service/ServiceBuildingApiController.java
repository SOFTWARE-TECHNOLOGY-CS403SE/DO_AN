package org.example.advancedrealestate_be.controller.api.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.ServiceDto;
import org.example.advancedrealestate_be.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@Tag(name = "Admin services", description = "API for service")
public class ServiceBuildingApiController {
    Service service;

    @Autowired
    public ServiceBuildingApiController(Service service) {
        this.service = service;
    }

    @PostMapping("/services")
    private ResponseEntity<JSONObject> create(@RequestBody ServiceDto serviceDto){
        JSONObject data = new JSONObject();
        try{
            ServiceDto responseDto = service.create(serviceDto);
            data.put("data", responseDto);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/services")
    private ResponseEntity<JSONObject> index(){
        JSONObject data = new JSONObject();
        try{
            List<ServiceDto> serviceDtoList = service.findAll();
            data.put("total" , serviceDtoList.size());
            data.put("data", serviceDtoList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/services/{id}")
    private ResponseEntity<JSONObject> detail(@PathVariable String id){
        JSONObject object = new JSONObject();
        try{
            ServiceDto responseDto = service.findById(id);
            object.put("data", responseDto);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception error){
            object.put("message", error.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/services/{id}")
    private ResponseEntity<JSONObject> update(@PathVariable String id, @RequestBody ServiceDto serviceDto){
        JSONObject data = new JSONObject();
        try{
            ServiceDto responseDto = service.updateById(id, serviceDto);
            data.put("data", responseDto);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/services/{id}")
    private ResponseEntity<JSONObject> remove(@PathVariable String id){
        JSONObject data = new JSONObject();
        try{
            ServiceDto responseDto = service.deleteById(id);
            data.put("data", responseDto);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
