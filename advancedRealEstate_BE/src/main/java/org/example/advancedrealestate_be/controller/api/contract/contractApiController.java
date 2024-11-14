package org.example.advancedrealestate_be.controller.api.contract;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.mapper.ContractMapper;
import org.example.advancedrealestate_be.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/contract")
@Tag(name = "Admin contract", description = "API for admin")
public class contractApiController {


    @Autowired
    private ContractService contractService;


    @PostMapping
    public ResponseEntity<JSONObject> createContract(@RequestBody ContractRequest request){
        JSONObject data=new JSONObject();
        try{
            ContractResponse response=contractService.createContract(request);
            data.put("data",response);
            data.put("message","Contract was created successfully");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getContractById(@PathVariable String id){
        JSONObject data=new JSONObject();
        try{
            ContractResponse response=contractService.getContractById(id);
            data.put("data",response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping
    public ResponseEntity<JSONObject> getAllContracts(){
        JSONObject data=new JSONObject();
        try{
            List<ContractResponse> contractResponses=contractService.getAllContracts();
            data.put("total", contractResponses.size());
            data.put("data",contractResponses);
            return new ResponseEntity<>(data,HttpStatus.OK);

        }catch (Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JSONObject> updateContract(@PathVariable String id, @RequestBody ContractRequest request){
        JSONObject data=new JSONObject();
        try {
            ContractResponse response=contractService.updateContract(id,request);
            data.put("data",response);
            data.put("message","Contract was updated successfully");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch(Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteContract(@PathVariable String id){
        JSONObject data=new JSONObject();
        try {
            contractService.deleteContract(id);
            data.put("message","Contract was deleted successfully");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch (Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<JSONObject> deleteContracts(@RequestBody List<String> ids) {
        JSONObject data = new JSONObject();
        try {
            contractService.deleteContracts(ids);
            data.put("message", "Contracts were deleted successfully");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
