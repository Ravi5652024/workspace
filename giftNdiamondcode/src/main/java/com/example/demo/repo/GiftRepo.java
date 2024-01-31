package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.GiftDetails;

public interface GiftRepo extends MongoRepository<GiftDetails, String> {

}
