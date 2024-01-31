package com.api.coolclub.models.request;

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
public class GetSubCategoriesReq {
    private String category;
    private String subCategory;
    private String type;
    private Integer page;
}
