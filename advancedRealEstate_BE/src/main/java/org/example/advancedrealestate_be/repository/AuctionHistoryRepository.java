package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.AuctionDetail;
import org.example.advancedrealestate_be.entity.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory,String> {
    @Query("SELECT CASE WHEN " +
           "COUNT(ah) > 0 THEN true ELSE false END " +
           "FROM AuctionHistory ah " +
           "WHERE ah.bidAmount IS NOT NULL " +
           "AND ah.identity_key IS NOT NULL")
    boolean existsByBidAmountAndIdentityHistoryKey();
}
