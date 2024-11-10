package org.example.advancedrealestate_be.controller.api.auction;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.AuctionDetailRequest;
import org.example.advancedrealestate_be.dto.request.AuctionHistoryRequest;
import org.example.advancedrealestate_be.service.AuctionDetailService;
import org.example.advancedrealestate_be.service.AuctionHistoryService;
import org.example.advancedrealestate_be.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/admin/")
@Tag(name = "Admin auction histories", description = "API for auction history")
@Slf4j
public class AuctionHistoryApiController {

    private final AuctionService auctionService;
    private final AuctionDetailService auctionDetailService;
    private final AuctionHistoryService auctionHistoryService;


    public AuctionHistoryApiController(AuctionService auctionService, AuctionDetailService auctionDetailService, AuctionHistoryService auctionHistoryService) {
        this.auctionService = auctionService;
        this.auctionDetailService = auctionDetailService;
        this.auctionHistoryService = auctionHistoryService;
    }

    @PostMapping("/auction-histories")
    private ResponseEntity<JSONObject> create(@RequestBody AuctionHistoryRequest dto) {
        return new ResponseEntity<>(auctionHistoryService.create(dto), HttpStatus.OK);
    }

    @PostMapping("/save-all-auction-histories")
    private ResponseEntity<JSONObject> create(@RequestBody List<AuctionHistoryRequest> dtos) {
        return new ResponseEntity<>(auctionHistoryService.saveAll(dtos), HttpStatus.OK);
    }

    @PostMapping("/handle-bid-messages")
    private ResponseEntity<JSONObject> handleBidMessages(@RequestBody List<AuctionHistoryRequest> dtos) {
        return new ResponseEntity<>(auctionHistoryService.handleBidMessages(dtos), HttpStatus.OK);
    }

    @GetMapping("/auction-histories")
    private ResponseEntity<JSONObject> index() {
        JSONObject responseObject = new JSONObject();
        responseObject.put("data", auctionHistoryService.findAll());
        responseObject.put("total", auctionHistoryService.findAll().size());
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/auction-histories/{id}")
    private ResponseEntity<JSONObject> detail(@PathVariable String id) {
        JSONObject responseObject = new JSONObject();
        responseObject.put("data", auctionHistoryService.findById(id));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PatchMapping("/auction-histories/{id}")
    private ResponseEntity<JSONObject> update(@PathVariable String id, @RequestBody AuctionHistoryRequest dto) {
        return new ResponseEntity<>(auctionHistoryService.updateById(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/auction-histories/{id}")
    private ResponseEntity<JSONObject> remove(@PathVariable String id) {
        return new ResponseEntity<>(auctionHistoryService.deleteById(id), HttpStatus.OK);
    }
}
