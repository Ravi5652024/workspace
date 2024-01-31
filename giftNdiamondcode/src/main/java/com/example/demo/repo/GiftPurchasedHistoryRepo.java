package com.example.demo.repo;

import com.example.demo.entity.GiftPurchasedHistory;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GiftPurchasedHistoryRepo extends MongoRepository<GiftPurchasedHistory, String> {

}
