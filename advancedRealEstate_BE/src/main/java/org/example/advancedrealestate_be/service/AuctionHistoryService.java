package org.example.advancedrealestate_be.service;

import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.AuctionDetailRequest;
import org.example.advancedrealestate_be.dto.request.AuctionHistoryRequest;
import org.example.advancedrealestate_be.dto.response.AuctionDetailResponse;
import org.example.advancedrealestate_be.dto.response.AuctionHistoryResponse;
import org.example.advancedrealestate_be.entity.Auction;
import org.example.advancedrealestate_be.entity.AuctionHistory;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AuctionHistoryService {
    List<AuctionHistoryResponse> findAll();

    JSONObject findById(String id);

    JSONObject create(AuctionHistoryRequest dto);

    JSONObject saveAll(List<AuctionHistoryRequest> dtos);

    void saveBidMessage(AuctionHistoryRequest dto);

    JSONObject handleBidMessages(List<AuctionHistoryRequest> dtos);

    JSONObject updateById(String id, AuctionHistoryRequest dto);

    JSONObject deleteById(String id);

    void handleBidMessage(AuctionHistoryRequest dto);

    void handleDeleteAllAuctionHistoriesByAid(String auctionId);
}