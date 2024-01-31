package com.api.coolclub.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.coolclub.entities.GlobalConfig;

/*
 * @Author Rohan_Sharma
*/

@Repository
public interface GlobalConfigRepo extends MongoRepository<GlobalConfig, String> {

    @Query(value = "{}", fields = "{ '_id': 0}")
    List<GlobalConfig> findAllKeyValues();
}
