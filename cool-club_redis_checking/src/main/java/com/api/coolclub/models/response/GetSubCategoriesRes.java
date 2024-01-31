package com.api.coolclub.models.response;

import org.springframework.stereotype.Component;

import com.api.coolclub.models.pojo.ImageResolutionPojo;

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
public class GetSubCategoriesRes {
    private String name;
    private ImageResolutionPojo thumbUrl;
    private ImageResolutionPojo fullpreviewUrl;
    private Float rating;
}
