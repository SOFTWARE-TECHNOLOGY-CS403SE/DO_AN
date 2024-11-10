package org.example.advancedrealestate_be.repository;

import org.example.advancedrealestate_be.entity.AuctionDetail;
import org.example.advancedrealestate_be.entity.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory,String> {
}
