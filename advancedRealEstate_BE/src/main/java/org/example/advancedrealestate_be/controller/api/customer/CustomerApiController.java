package org.example.advancedrealestate_be.controller.api.customer;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.CustomerRequest;
import org.example.advancedrealestate_be.dto.response.CustomerResponse;
import org.example.advancedrealestate_be.dto.response.TypeBuildingResponse;
import org.example.advancedrealestate_be.entity.Customers;
import org.example.advancedrealestate_be.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/customer")
@Tag(name = "Admin customer", description = "API for admin")
public class CustomerApiController {
    @Autowired
    private CustomerService customerService;

//    @GetMapping
//    public ResponseEntity<JSONObject> getAllCustomers() {
//        JSONObject data=new JSONObject();
//        try{
//            List<CustomerResponse> customerResponses=customerService.getAllCustomers();
//            data.put("total",customerResponses.size());
//            data.put("data",customerResponses);
//            return new ResponseEntity<>(data,HttpStatus.OK);
//        }catch(Exception error){
//            data.put("message",error.getMessage());
//            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping
    public ResponseEntity<JSONObject> getAllCustomers(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        JSONObject data = new JSONObject();
        // Lấy dữ liệu người dùng với phân trang
        Page<CustomerResponse> pageResult = customerService.getAllCustomers(page, size);

        // Tạo đối tượng response chứa thông tin phân trang và danh sách người dùng
        Map<String, Object> response = new HashMap<>();

        // Metadata về phân trang
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("total", pageResult.getTotalElements());
        pagination.put("per_page", pageResult.getSize());
        pagination.put("current_page", pageResult.getNumber() + 1);
        pagination.put("last_page", pageResult.getTotalPages());
        pagination.put("from", (pageResult.getNumber() * pageResult.getSize()) + 1);
        pagination.put("to", Math.min((pageResult.getNumber() + 1) * pageResult.getSize(), pageResult.getTotalElements()));

        response.put("pagination", pagination);
        response.put("data", pageResult.getContent());

        try {
            data.put("status", 200);
            data.put("data", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error){
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getCustomerById(@PathVariable String id) {
        JSONObject data=new JSONObject();
        try{
            CustomerResponse response=customerService.getCustomerById(id);
            data.put("data",response);
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity<JSONObject> createCustomer(@RequestBody CustomerRequest request) {
        JSONObject data=new JSONObject();
        try{
            CustomerResponse response=customerService.createCustomer(request);
            data.put("data",response);
            data.put("message","Customer was created successfully");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch (Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JSONObject> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest request) {
        JSONObject data=new JSONObject();
        try{
            CustomerResponse response=customerService.updateCustomer(id,request);
            data.put("data",response);
            data.put("message","Customer was updated successfully");
                    return new ResponseEntity<>(data,HttpStatus.OK);

        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteCustomer(@PathVariable String id) {
        JSONObject data=new JSONObject();
        try{
            customerService.deleteCustomer(id);
            data.put("message", "Customer was deleted successfully  !");
            return new ResponseEntity<>(data,HttpStatus.OK);

        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<JSONObject> deleteListCustomers(@RequestBody List<String> ids){
        JSONObject data=new JSONObject();
        try{
            customerService.deleteCustomers(ids);
            data.put("message","Customers were deleted successfully ");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }catch(Exception error){
            data.put("message",error.getMessage());
            return new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
