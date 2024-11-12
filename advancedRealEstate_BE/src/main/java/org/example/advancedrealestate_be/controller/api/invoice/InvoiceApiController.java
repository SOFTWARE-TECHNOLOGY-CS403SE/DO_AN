package org.example.advancedrealestate_be.controller.api.invoice;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.InvoiceRequest;
import org.example.advancedrealestate_be.dto.response.InvoiceResponse;
import org.example.advancedrealestate_be.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="bearerAuth")
@RequestMapping("/api/invoice")
@Tag(name="User invoice", description = " API for user")
public class InvoiceApiController {


    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<JSONObject> createInvoice(@RequestBody InvoiceRequest request) {
        JSONObject data = new JSONObject();
        try {
            InvoiceResponse response = invoiceService.createInvoice(request);
            data.put("data", response);
            data.put("message", "Invoice created successfully");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getInvoiceById(@PathVariable String id) {
        JSONObject data = new JSONObject();
        try {
            InvoiceResponse response = invoiceService.getInvoiceById(id);
            data.put("data", response);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<JSONObject> getAllInvoices() {
        JSONObject data = new JSONObject();
        try {
            List<InvoiceResponse> invoiceList = invoiceService.getAllInvoice();
            data.put("total", invoiceList.size());
            data.put("data", invoiceList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JSONObject> updateInvoice(
            @PathVariable String id,
            @RequestBody InvoiceRequest request) {
        JSONObject data = new JSONObject();
        try {
            InvoiceResponse response = invoiceService.updateInvoice(id, request);
            data.put("data", response);
            data.put("message", "Invoice updated successfully");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteInvoice(@PathVariable String id) {
        JSONObject data = new JSONObject();
        try {
            invoiceService.deleteInvoice(id);
            data.put("message", "Invoice deleted successfully");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception error) {
            data.put("message", error.getMessage());
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
