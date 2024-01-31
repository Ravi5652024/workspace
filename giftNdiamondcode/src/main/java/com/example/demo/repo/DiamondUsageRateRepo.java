package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.DiamondUsageRate;

public interface DiamondUsageRateRepo extends MongoRepository<DiamondUsageRate, String> {

}
