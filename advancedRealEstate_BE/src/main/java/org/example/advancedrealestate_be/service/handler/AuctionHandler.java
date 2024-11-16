package org.example.advancedrealestate_be.service.handler;

import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.request.AuctionRequest;
import org.example.advancedrealestate_be.dto.request.BuildingUpdateRequest;
import org.example.advancedrealestate_be.dto.request.CreateBuildingRequest;
import org.example.advancedrealestate_be.dto.response.AuctionResponse;
import org.example.advancedrealestate_be.entity.Auction;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.Map;
import org.example.advancedrealestate_be.entity.User;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.AuctionMapper;
import org.example.advancedrealestate_be.mapper.BuildingMapper;
import org.example.advancedrealestate_be.repository.AuctionDetailRepository;
import org.example.advancedrealestate_be.repository.AuctionRepository;
import org.example.advancedrealestate_be.repository.BuildingRepository;
import org.example.advancedrealestate_be.repository.UserRepository;
import org.example.advancedrealestate_be.service.AuctionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuctionHandler implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionDetailRepository auctionDetailRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuctionHandler(AuctionRepository auctionRepository, AuctionDetailRepository auctionDetailRepository, BuildingRepository buildingRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.auctionRepository = auctionRepository;
        this.auctionDetailRepository = auctionDetailRepository;
        this.buildingRepository = buildingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AuctionResponse> findAll() {
        List<Auction> auctionList = auctionRepository.findAll();

        return auctionList.stream()
                .map(AuctionMapper::mapToAuction)
                .collect(Collectors.toList());
    }

    @Override
    public AuctionResponse findById(String id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));

        return new AuctionResponse(
                auction.getId(),
                auction.getName(),
                auction.getStart_date(),
                auction.getStart_time(),
                auction.getEnd_time(),
                auction.getDescription(),
                auction.isActive(),
                auction.getBuilding() != null ? auction.getBuilding() : null,
                auction.getBuilding() != null ? auction.getBuilding().getMap() : null,
                auction.getUserCreatedBy(),
                auction.getIdentity_key()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject create(AuctionRequest auctionRequest) {
        JSONObject responseObject = new JSONObject();
        Auction auction = new Auction();
        User userCreatedBy = userRepository.findById(auctionRequest.getUserCreatedBy()).orElse(null);
//        Building building = buildingRepository.findById(auctionRequest.getBuilding_id()).orElse(null);
        auction.setName(auctionRequest.getName());
        auction.setStart_date(auctionRequest.getStart_date());
        auction.setStart_time(auctionRequest.getStart_time());
        auction.setEnd_time(auctionRequest.getEnd_time());
        auction.setDescription(auctionRequest.getDescription());
        auction.setActive(false);
        auction.setUserCreatedBy(userCreatedBy);
        auction.setIdentity_key(generateAuctionIdKey(10));
//        auction.setBuilding(building);
        Auction auctionNew = auctionRepository.save(auction);
        responseObject.put("data", auctionNew);
        return responseObject;
    }

    public static String generateAuctionIdKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(length));
        }
        return "Aid"+key;
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject updateById(String id, AuctionRequest auctionRequest) {
        JSONObject responseObject = new JSONObject();
        Building building = buildingRepository.findById(auctionRequest.getBuilding_id()).orElse(null);
        User user = userRepository.findById(auctionRequest.getUserCreatedBy()).orElse(null);
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));
        auction.setName(auctionRequest.getName());
        auction.setStart_date(auctionRequest.getStart_date());
        auction.setStart_time(auctionRequest.getStart_time());
        auction.setEnd_time(auctionRequest.getEnd_time());
        auction.setDescription(auctionRequest.getDescription());
        auction.setActive(auctionRequest.isActive());
        auction.setBuilding(building);
        auction.setUserCreatedBy(user);
        Auction auctionUpdated = auctionRepository.save(auction);
        responseObject.put("data", auctionUpdated);
        responseObject.put("message", "Update successfully!");
        return responseObject;
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Override
    public JSONObject deleteById(String id) {
        JSONObject responseObject = new JSONObject();
        auctionRepository.deleteById(id);
        responseObject.put("message", "Delete successfully!");
        return responseObject;
    }
}
