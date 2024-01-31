package com.api.coolclub.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.coolclub.entities.OperatorConfig;
import com.api.coolclub.models.pojo.MaxOperatorUuidPojo;

/*
 * @Author Rohan_Sharma
*/

@Repository
public interface OperatorConfigRepo extends MongoRepository<OperatorConfig, String> {
    
    List<OperatorConfig> findAll();

    @Query(value = "{}", fields = "{'_id': 0, 'uuid': 1, 'name': 1,'defaultLanguage': 1, 'languageSupport': 1}")
    List<OperatorConfig> findAllNameLang();

    Optional<OperatorConfig> findByUuid(Integer uuid);

    long countByOperatorAndCountry(String operator, String country);

    OperatorConfig findByOperatorAndCountry(String operator, String country);
    
    @Query(value = "{ 'operator' : ?0, 'country' : ?1 }", fields = "{ 'cmsUsername' : 1, 'cmsPassword' : 1, 'lastActiveJWT' : 1 }")
    OperatorConfig findcmsUsernamePasswordAndJwt(String operator, String country);

    @Aggregation(pipeline = {
        "{$group: {_id: null, maxUuid: {$max: '$uuid'}}}",
        "{$project: {_id: 0, maxUuid: 1}}"
    })
    Optional<MaxOperatorUuidPojo> findMaxUuid();
}
