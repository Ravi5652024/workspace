package com.api.coolclub.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.coolclub.entities.OtpHistory;

/*
 * @Author Rohan_Sharma
*/

@Repository
public interface OtpHistoryRepo extends MongoRepository<OtpHistory, String> {
    
    OtpHistory findByUserName(String userName);

    @Query(value = "{ 'userName' : ?0 }", fields = "{ 'otp' : 1, 'creationTime' : 1, '_id' : 0 }")
    Optional<OtpHistory> findOtpAndcreationTimeByUserName(String userName);

    @Transactional
    @Query(value = "{ 'userName' : ?0 }", delete = true)
    void deleteByUserName(String userName);
}
