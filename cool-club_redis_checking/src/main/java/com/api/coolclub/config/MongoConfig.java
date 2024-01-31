package com.api.coolclub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/*
 * @Author Rohan_Sharma
*/

@Configuration
@EnableMongoAuditing
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MongoConfig {

}
