package com.example.demo.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DiamondDetails;

@Repository
public interface DiamondRepo  extends MongoRepository<DiamondDetails,String>{

}
