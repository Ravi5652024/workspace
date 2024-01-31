package com.api.coolclub.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.coolclub.utils.InstantTypeAdapter;
import com.api.coolclub.utils.LocalDateTimeTypeAdapter;
import com.api.coolclub.utils.LocalDateTypeAdapter;
import com.api.coolclub.utils.LocalTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * @Author Rohan_Sharma
*/

@Configuration
public class GsonConfig {

    @Bean
    public Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                // Add other type adapters if needed
                .create();
    }
}
