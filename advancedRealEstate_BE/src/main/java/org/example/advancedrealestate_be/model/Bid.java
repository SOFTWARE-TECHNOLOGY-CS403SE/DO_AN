package org.example.advancedrealestate_be.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Bid {


    private Bid.BidMessageType type;
    private double bidAmount;
    private String sender;
    private String email;
    private String auction_id;
    private String client_id;

    public enum BidMessageType {
        JOIN,
        LEAVE,
        AUCTION
    }
}
