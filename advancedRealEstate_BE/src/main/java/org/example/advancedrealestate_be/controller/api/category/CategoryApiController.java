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
import org.example.advancedrealestate_be.service.CategoryService;
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
@RequestMapping("api/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name="Admin Category Device")
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public ResponseEntity<JSONObject> getAllCategory(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        JSONObject data = new JSONObject();
        Map<String, Object> response = new HashMap<>();

        try {
            if (page == null || size == null) {
                List<CategoryResponse> categories = categoryService.getAllCategories();

                response.put("data", categories);
            } else {
                Page<CategoryResponse> pageResult = categoryService.getCategory(page, size);

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


    @PostMapping
    public ResponseEntity<JSONObject> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
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
    public ResponseEntity<JSONObject> updateCategory(@Valid @PathVariable String id, @RequestBody CategoryUpdateRequest request) {
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
    public ResponseEntity<JSONObject> deleteCategory(@PathVariable String id) {
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
    public ResponseEntity<JSONObject> deleteAllCategory(@Valid @RequestBody DeleteCategoryRequest request) {
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
