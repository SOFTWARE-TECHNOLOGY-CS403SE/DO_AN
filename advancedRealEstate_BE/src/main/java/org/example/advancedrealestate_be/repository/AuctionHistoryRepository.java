package org.example.advancedrealestate_be.repository;

import jakarta.transaction.Transactional;
import org.example.advancedrealestate_be.entity.AuctionDetail;
import org.example.advancedrealestate_be.entity.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory,String> {
    @Query("SELECT CASE WHEN " +
           "COUNT(ah) > 0 THEN true ELSE false END " +
           "FROM AuctionHistory ah " +
           "WHERE ah.bidAmount IS NOT NULL " +
           "AND ah.identity_key IS NOT NULL")
    boolean existsByBidAmountAndIdentityHistoryKey();

    @Modifying
    @Transactional
    @Query("DELETE FROM AuctionHistory as ah WHERE ah.identity_key = :identity_key")
    void deleteAuctionHistoriesByIdentity_key(@Param("identity_key") String identity_key);
}
