package org.example.advancedrealestate_be.service;

import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.AuctionRequest;
import org.example.advancedrealestate_be.dto.request.BuildingUpdateRequest;
import org.example.advancedrealestate_be.dto.request.CreateBuildingRequest;
import org.example.advancedrealestate_be.dto.response.AuctionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuctionService {

    List<AuctionResponse> findAll();

    AuctionResponse findById(String id);

    JSONObject create(AuctionRequest dto);

    JSONObject updateById(String id, AuctionRequest dto);

    JSONObject deleteById(String id);
}
