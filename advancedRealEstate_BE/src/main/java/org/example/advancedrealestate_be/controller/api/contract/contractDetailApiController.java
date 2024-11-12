package org.example.advancedrealestate_be.controller.api.contract;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.repository.ContractDetailsRepository;
import org.example.advancedrealestate_be.service.ContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/contractDetail")
@Tag(name = "Admin contractDetail", description = "API for admin")
public class contractDetailApiController {
    @Autowired
    private ContractDetailService contractDetailService;

    @PostMapping
    public ResponseEntity<JSONObject> createContractDetail(@RequestBody ContractDetailRequest request) {
        JSONObject data=new JSONObject();
        try{
            ContractDetailResponse response=contractDetailService.createContractDetail(request);
            data.put("data",response);
            data.put("message","Contract details created successfully !");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch(Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getContractDetailById(@PathVariable String id) {
        JSONObject data=new JSONObject();
        try{
            ContractDetailResponse response=contractDetailService.getContractDetailById(id);
            data.put("data",response);
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch (Exception e){
            data.put("message",e.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping
    public ResponseEntity<JSONObject> getAllContractDetails() {
       JSONObject data=new JSONObject();
       try {
           List<ContractDetailResponse> contractDetailResponseList=contractDetailService.getAllContractDetails();
           data.put("total",contractDetailResponseList.size());
           data.put("data",contractDetailResponseList);
           return new ResponseEntity<>(data,HttpStatus.OK);

       }catch (Exception e){
           data.put("message",e.getMessage());
           return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

       }

    }

    @PutMapping("/{id}")
    public ResponseEntity<JSONObject> updateContractDetail(
            @PathVariable String id,
            @RequestBody ContractDetailRequest request) {
        JSONObject data=new JSONObject();
        try{
            ContractDetailResponse response=contractDetailService.updateContractDetail(id,request);
            data.put("data",response);
            data.put("message","Contract was updated successfully !");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch (Exception e){
            data.put("message",e.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteContractDetail(@PathVariable String id) {
        JSONObject data=new JSONObject();
        try{
            contractDetailService.deleteContractDetail(id);
            data.put("message","Contract detail was deleted successfully !");
            return new ResponseEntity<>(data,HttpStatus.OK);

        }catch (Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
