package com.api.coolclub.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author Rohan_Sharma
*/

@Document(collection = "screen_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenConfig {
    @Id
    private String id;

    @Indexed(unique = true, sparse = true)
    private String key;

    private String value;

    private Integer operatorUuId;

    private String currentVersion;
}
