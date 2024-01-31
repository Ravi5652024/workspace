package com.api.coolclub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.api.coolclub.entities.UserProfile;
import java.util.List;
import java.util.Optional;



/*
 * @Author Rohan_Sharma
*/

@Repository
public interface UserProfileRepo extends MongoRepository<UserProfile, String>{
    
    Optional<UserProfile> findById(String userId);

    long countByMsisdn(String msisdn);
    UserProfile findByMsisdn(String msisdn);

    long countByEmail(String email);
    UserProfile findByEmail(String email);

    long countByNickName(String nickName);
}
