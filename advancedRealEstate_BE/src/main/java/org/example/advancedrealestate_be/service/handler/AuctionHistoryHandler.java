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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionHistoryHandler implements AuctionHistoryService {


    private final AuctionDetailRepository auctionDetailRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuctionRepository auctionRepository;
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

    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
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

    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
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

    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Override
    public JSONObject handleBidMessages(List<AuctionHistoryRequest> dtos) {
        JSONObject responseObject = new JSONObject();
        List<AuctionHistory> auctionHistoryList = dtos.stream().map(dto -> {
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
        List<AuctionHistory>  auctionHistoryListNew = auctionHistoryRepository.saveAll(auctionHistoryList);
        handleClientWin(auctionHistoryListNew);
        responseObject.put("message", "Create auction histories successfully");
        return responseObject;
    }

    public void handleClientWin(List<AuctionHistory> auctionHistoryListNew) {
        Optional<AuctionHistory> maxBidAmount = auctionHistoryListNew.stream()
        .max(Comparator.comparing(AuctionHistory::getBidAmount));
        AuctionHistory clientWin= maxBidAmount.orElse(null);
        AuctionDetail auctionDetail = new AuctionDetail();
        assert clientWin != null;
        String auctionName = clientWin.getAuction() == null ? null : clientWin.getAuction().getName();
        Auction auction = clientWin.getAuction() == null ? null : clientWin.getAuction();
        User client = clientWin.getClient() == null ? null : clientWin.getClient();
        auctionDetail.setStatus("notConfirmed");
        auctionDetail.setNote("Bạn là người chiến thắng trong phiên đấu giá: " + auctionName);
        auctionDetail.setResult("win");
        auctionDetail.setBidAmount(clientWin.getBidAmount());
        auctionDetail.setAuction(auction);
        auctionDetail.setClient(client);
        AuctionDetail auctionDetailNew = auctionDetailRepository.save(auctionDetail);

    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
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

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
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

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject deleteById(String id) {
        JSONObject responseObject = new JSONObject();
        auctionHistoryRepository.deleteById(id);
        responseObject.put("message", "Delete successfully!");
        return responseObject;
    }
}
