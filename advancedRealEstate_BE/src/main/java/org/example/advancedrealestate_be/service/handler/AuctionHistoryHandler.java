package org.example.advancedrealestate_be.service.handler;


import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.AuctionHistoryRequest;
import org.example.advancedrealestate_be.dto.response.AuctionHistoryResponse;
import org.example.advancedrealestate_be.entity.Auction;
import org.example.advancedrealestate_be.entity.AuctionDetail;
import org.example.advancedrealestate_be.entity.AuctionHistory;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.AuctionHistoryMapper;
import org.example.advancedrealestate_be.repository.AuctionDetailRepository;
import org.example.advancedrealestate_be.repository.AuctionHistoryRepository;
import org.example.advancedrealestate_be.repository.AuctionRepository;
import org.example.advancedrealestate_be.repository.UserRepository;
import org.example.advancedrealestate_be.service.AuctionHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class AuctionHistoryHandler implements AuctionHistoryService {


    private final AuctionDetailRepository auctionDetailRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuctionRepository auctionRepository;
    private final Lock lock = new ReentrantLock();
    @Autowired
    public AuctionHistoryHandler(AuctionDetailRepository auctionDetailRepository, AuctionHistoryRepository auctionHistoryRepository, UserRepository userRepository, ModelMapper modelMapper, AuctionRepository auctionRepository) {
        this.auctionDetailRepository = auctionDetailRepository;
        this.auctionHistoryRepository = auctionHistoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.auctionRepository = auctionRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public List<AuctionHistoryResponse> findAll() {
        List<AuctionHistory> auctionDetailList = auctionHistoryRepository.findAll();

        return auctionDetailList.stream()
                .map(AuctionHistoryMapper::mapToAuctionHistory)
                .collect(Collectors.toList());
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Override
    public AuctionHistoryResponse findById(String id) {
        AuctionHistory auctionHistory = auctionHistoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_HISTORY_NOT_FOUND));

        return new AuctionHistoryResponse(
                auctionHistory.getId(),
                auctionHistory.getBidAmount(),
                auctionHistory.getBidTime(),
                auctionHistory.getMessageBidId(),
                auctionHistory.getAuction(),
                auctionHistory.getClient()
        );
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Override
    public JSONObject create(AuctionHistoryRequest dto) {
        JSONObject responseObject = new JSONObject();
        AuctionHistory auctionHistory = modelMapper.map(dto, AuctionHistory.class);
        AuctionHistory auctionDetailNew = auctionHistoryRepository.save(auctionHistory);
        responseObject.put("data", auctionDetailNew);
        return responseObject;
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Override
    public void saveBidMessage(AuctionHistoryRequest dto) {
        Auction auction = auctionRepository.findById(dto.getAuction_id()).orElse(null);
        User client = userRepository.findById(dto.getClient_id()).orElse(null);
        AuctionHistory auctionHistory = new AuctionHistory();
        auctionHistory.setMessageBidId(dto.getMessageBidId());
        auctionHistory.setBidTime(dto.getBidTime());
        auctionHistory.setBidAmount(dto.getBidAmount());
        auctionHistory.setAuction(auction);
        auctionHistory.setClient(client);
        AuctionHistory auctionDetailNew = auctionHistoryRepository.save(auctionHistory);
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Override
    public JSONObject handleBidMessages(List<AuctionHistoryRequest> dtos) {
        JSONObject responseObject = new JSONObject();
        if(dtos.isEmpty()){
            System.err.println("dtos null");
            throw new AppException(ErrorCode.AUCTION_HISTORY_LIST_IS_EMPTY);
        }
        List<AuctionHistory> auctionHistoryList = dtos.stream().map(dto -> {
            Auction auction = auctionRepository.findById(dto.getAuction_id()).orElse(null);
            User client = userRepository.findById(dto.getClient_id()).orElse(null);
            AuctionHistory auctionHistory = new AuctionHistory();
            auctionHistory.setBidAmount(dto.getBidAmount());
            auctionHistory.setBidTime(dto.getBidTime());
            auctionHistory.setMessageBidId(dto.getMessageBidId());
            auctionHistory.setAuction(auction);
            auctionHistory.setClient(client);
            assert auction != null;
            auctionHistory.setIdentity_key(auction.getIdentity_key());
            return auctionHistory;
        }).collect(Collectors.toList());
        if(auctionHistoryList.isEmpty()){
            return responseObject;
        }
        List<AuctionHistory>  auctionHistoryListNew = auctionHistoryRepository.saveAll(auctionHistoryList);

        List<AuctionHistory> allHistories = auctionHistoryRepository.findAll();
        Set<String> uniqueHistoryKeys = new HashSet<>();
        List<AuctionHistory> duplicateHistories = new ArrayList<>();
        for (AuctionHistory history : allHistories) {
            String key = history.getBidAmount() + "-" + history.getIdentity_key();
            if (!uniqueHistoryKeys.add(key)) {
                duplicateHistories.add(history);
            }
        }
        auctionHistoryRepository.deleteAll(duplicateHistories);
        handleClientWin(allHistories);
        responseObject.put("message", "Create auction histories successfully");

        return responseObject;
    }

    public void handleClientWin(List<AuctionHistory> auctionHistoryListNew) {
        double maxBidAmount = auctionHistoryListNew.stream()
        .mapToDouble(AuctionHistory::getBidAmount).max().orElse(0);
        if(maxBidAmount <= 0){
            System.err.println("maxBidAmount = 0");
            throw new AppException(ErrorCode.AUCTION_HISTORY_LIST_IS_EMPTY);
        }
        Map<User, AuctionHistory> highestBidByUser = auctionHistoryListNew.stream()
        .collect(Collectors.toMap(AuctionHistory::getClient,
            auctionHistory -> auctionHistory,
            (existing, replacement) -> existing.getBidAmount() >=
            replacement.getBidAmount() ? existing : replacement
        ));
        List<AuctionDetail> auctionDetailListNew = highestBidByUser.values().stream().map(auctionHistory -> {
            AuctionDetail auctionDetail = new AuctionDetail();
            String auctionName = auctionHistory.getAuction() != null ? auctionHistory.getAuction().getName() : null;
            Auction auction = auctionHistory.getAuction();
            User client = auctionHistory.getClient();

            auctionDetail.setAuction(auction);
            auctionDetail.setClient(client);
            auctionDetail.setStatus("notConfirmed");
            auctionDetail.setBidAmount(auctionHistory.getBidAmount());
            auctionDetail.setIdentity_key(auction.getIdentity_key());
            if (auctionHistory.getBidAmount() == maxBidAmount) {
                auctionDetail.setResult("win");
                auctionDetail.setNote("Xin chúc mừng bạn là người chiến thắng trong phiên " + auctionName);
            }
            if(auctionHistory.getBidAmount() < maxBidAmount){
                auctionDetail.setResult("lose");
                auctionDetail.setNote("Bạn đã thua cuộc trong phiên " + auctionName + ", chúc bạn may mắn lần sau!");
            }
            return auctionDetail;
        }).collect(Collectors.toList());
        auctionDetailRepository.saveAll(auctionDetailListNew);
        List<AuctionDetail> allAuctionDetail = auctionDetailRepository.findAll();
        Set<String> uniqueDetailKeys = new HashSet<>();
        List<AuctionDetail> duplicateDetails = new ArrayList<>();
        for (AuctionDetail detail : allAuctionDetail) {
            String key = detail.getBidAmount() + "-" + detail.getIdentity_key();
            if (!uniqueDetailKeys.add(key)) {
                duplicateDetails.add(detail);
            }
        }
        auctionDetailRepository.deleteAll(duplicateDetails);
    }

    @Override
    public void handleBidMessage(AuctionHistoryRequest dto) {
        AuctionHistory auctionHistory = new AuctionHistory();
        Auction auction = auctionRepository.findById(dto.getAuction_id()).orElse(null);
        User client = userRepository.findById(dto.getClient_id()).orElse(null);
        auctionHistory.setMessageBidId(dto.getMessageBidId());
        auctionHistory.setBidTime(dto.getBidTime());
        auctionHistory.setBidAmount(dto.getBidAmount());
        auctionHistory.setAuction(auction);
        auctionHistory.setClient(client);
//        auctionHistory.setIdentity_history_key();
        AuctionHistory auctionHistoryNew = auctionHistoryRepository.save(auctionHistory);
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject saveAll(List<AuctionHistoryRequest> dtos) {
        JSONObject responseObject = new JSONObject();
        List<AuctionHistory> auctionHistories = dtos.stream().map(dto -> {
            Auction auction = auctionRepository.findById(dto.getAuction_id()).orElse(null);
            User client = userRepository.findById(dto.getClient_id()).orElse(null);
            AuctionHistory auctionHistory = new AuctionHistory();
            auctionHistory.setBidAmount(dto.getBidAmount());
            auctionHistory.setBidTime(dto.getBidTime());
            auctionHistory.setMessageBidId(dto.getMessageBidId());
            auctionHistory.setAuction(auction);
            auctionHistory.setClient(client);
            return auctionHistory;
        }).collect(Collectors.toList());
        List<AuctionHistory>  auctionHistoryListNew = auctionHistoryRepository.saveAll(auctionHistories);
        responseObject.put("data", auctionHistoryListNew);
        return responseObject;
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject updateById(String id, AuctionHistoryRequest dto) {
        JSONObject responseObject = new JSONObject();
        Auction auction = auctionRepository.findById(dto.getAuction_id()).orElse(null);
        User client = userRepository.findById(dto.getClient_id()).orElse(null);
        AuctionHistory auctionHistory = auctionHistoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_HISTORY_NOT_FOUND));
        auctionHistory.setBidAmount(dto.getBidAmount());
        auctionHistory.setBidTime(dto.getBidTime());
        auctionHistory.setMessageBidId(dto.getMessageBidId());
        auctionHistory.setAuction(auction);
        auctionHistory.setClient(client);
        AuctionHistory auctionHistoryUpdated = auctionHistoryRepository.save(auctionHistory);
        responseObject.put("data", auctionHistoryUpdated);
        responseObject.put("message", "Update successfully!");
        return responseObject;
    }

//    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject deleteById(String id) {
        JSONObject responseObject = new JSONObject();
        AuctionHistory auctionHistory = auctionHistoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_HISTORY_NOT_FOUND));
        auctionHistoryRepository.delete(auctionHistory);
        responseObject.put("message", "Delete successfully!");
        return responseObject;
    }

}
