package com.api.coolclub.models.pojo;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author Rohan_Sharma
*/

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesHelperPojo {
    private String name; 
    private Integer position;
    private ImageResolutionPojo thumbUrl;
    private String subtitle;
}