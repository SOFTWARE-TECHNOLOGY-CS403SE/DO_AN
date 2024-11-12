package org.example.advancedrealestate_be.controller.api.category;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.*;
import org.example.advancedrealestate_be.dto.response.CategoryResponse;
import org.example.advancedrealestate_be.dto.response.TypeBuildingResponse;
import org.example.advancedrealestate_be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name="Category Device")
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public ResponseEntity<JSONObject> getAllTypeBuildings(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        JSONObject data = new JSONObject();
        // Lấy dữ liệu người dùng với phân trang
        Page<CategoryResponse> pageResult = categoryService.getCategory(page, size);

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

    @PostMapping
    public ResponseEntity<JSONObject> createTypeBuilding(@Valid @RequestBody CategoryCreateRequest request) {
        JSONObject data = new JSONObject();
        try{
            //trả message từ service về cho controller trả ra cho client
            String response = categoryService.createCategory(request);
            data.put("status", 200);
            data.put("message", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JSONObject> updateTypeBuilding(@Valid @PathVariable String id, @RequestBody CategoryUpdateRequest request) {
        JSONObject data = new JSONObject();
        try {
            String response = categoryService.updateCategory(id, request);
            data.put("status", 200);
            data.put("message", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception erro) {
            data.put("message", erro.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteTypeBuilding(@PathVariable String id) {
        JSONObject data = new JSONObject();
        try {
            String response = categoryService.deleteCategory(id);
            data.put("status", 200);
            data.put("message", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<JSONObject> deleteAllTypeBuilding(@Valid @RequestBody DeleteCategoryRequest request) {
        JSONObject data = new JSONObject();
        try {
            String response = categoryService.deleteCategorys(request);
            data.put("status", 200);
            data.put("message", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
