package org.example.advancedrealestate_be.dto.response;


import lombok.*;
import org.example.advancedrealestate_be.entity.Auction;
import org.example.advancedrealestate_be.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuctionHistoryResponse {
    private String id;
    private double bidAmount;
    private String bidTime;
    private String messageBidId;
    private Auction auction;
    private User client;
}
