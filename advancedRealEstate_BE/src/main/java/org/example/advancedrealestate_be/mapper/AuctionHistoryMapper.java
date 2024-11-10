package org.example.advancedrealestate_be.mapper;

import org.example.advancedrealestate_be.dto.response.AuctionDetailResponse;
import org.example.advancedrealestate_be.dto.response.AuctionHistoryResponse;
import org.example.advancedrealestate_be.entity.Auction;
import org.example.advancedrealestate_be.entity.AuctionDetail;
import org.example.advancedrealestate_be.entity.AuctionHistory;
import org.example.advancedrealestate_be.entity.User;

import java.util.Optional;

public class AuctionHistoryMapper {

    public static AuctionHistoryResponse mapToAuctionHistory(AuctionHistory auctionHistory) {

        Auction auction = auctionHistory.getAuction() == null ? null :
                auctionHistory.getAuction();
        User client = auctionHistory.getClient() == null ? null :
                auctionHistory.getClient();
        AuctionHistoryResponse dto = AuctionHistoryResponse.builder()
                .id(auctionHistory.getId())
                .bidAmount(auctionHistory.getBidAmount())
                .bidTime(auctionHistory.getBidTime())
                .messageBidId(auctionHistory.getMessageBidId())
                .auction(auction)
                .client(client)
                .build();
        if (dto != null) {

            return dto;

        } else {

            System.out.println(Optional.empty());

            return null;
        }
    }
}
