package com.api.coolclub.redis;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/*
 * @Author Rohan_Sharma
*/

@Service
public class RedisService {
    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    private final RedisTemplate<String, String> redisTemplate;
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addJson(String key, String json) {
        try {
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            log.error("-- [REDIS - addScreenJson] : ERROR",e);
        }
    }

    public Optional<String> getJson(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key));
        } catch (Exception e) {
            log.error("-- [REDIS - getScreenJson] : ERROR",e);
            return null;
        }
        
    }

    public void updateJson(String key, String json) {
        try {
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            log.error("-- [REDIS - updateScreenJson] : ERROR",e);
        }
    }

    public void deleteJson(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("-- [REDIS - deleteScreenJson] : ERROR",e);
        }
    }
}
