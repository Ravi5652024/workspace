package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.DiamondPurchasedHistory;


public interface DiamondPurchasedHistoryRepo extends MongoRepository<DiamondPurchasedHistory, String> {

}
