package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.BuildingDto;
import org.example.advancedrealestate_be.dto.response.AuctionResponse;
import org.example.advancedrealestate_be.entity.Auction;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.Map;
import org.example.advancedrealestate_be.entity.User;

import java.util.Optional;

public class AuctionMapper {

    public static AuctionResponse mapToAuction(Auction auction) {

        Map map = auction.getBuilding() == null ? null :
                auction.getBuilding().getMap();
        User user = auction.getUserCreatedBy() == null ? null :
                auction.getUserCreatedBy();
        AuctionResponse auctionResponse = AuctionResponse.builder()
                .id(auction.getId())
                .name(auction.getName())
                .start_date(auction.getStart_date())
                .start_time(auction.getStart_time())
                .end_time(auction.getEnd_time())
                .description(auction.getDescription())
                .isActive(auction.isActive())
                .building(auction.getBuilding())
                .map(map)
                .userCreatedBy(user)
                .build();
        if (auctionResponse != null) {

            return auctionResponse;

        } else {

            System.out.println(Optional.empty());

            return null;
        }
    }
}