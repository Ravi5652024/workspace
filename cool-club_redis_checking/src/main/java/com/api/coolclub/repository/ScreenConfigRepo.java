package com.api.coolclub.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.coolclub.entities.ScreenConfig;

/*
 * @Author Rohan_Sharma
*/

@Repository
public interface ScreenConfigRepo extends MongoRepository<ScreenConfig, String> {
    
    @Query(value = "{}", fields = "{ '_id': 0}")
    List<ScreenConfig> findAllKeyValues();

    @Query(value = "{'key': ?0, 'operatorUuId': ?1}", fields = "{ '_id': 0, 'key': 1, 'value': 1}")
    ScreenConfig findByKetAndOperatorUuId(String key,Integer operatorId);

    @Query(value = "{}", fields = "{ '_id': 0, 'key': 1, 'currentVersion': 1}")
    List<ScreenConfig> getAllKeyAndCurrentVersion();
}
