package com.api.coolclub.models.pojo;

import java.util.List;

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
public class CategoriesPojo {
    private String title;
    private String type;
    private String defaultTitleColor = "#DEDEDE";
    private String subtitle = "Categories";
    private String subititleTextColor = "#000000";
    private String colorText = "#DEDEDEDE";
    private String subcategoryTextColor = "#FFFFFFF";
    private List<Integer> coloredCharacterPosition = List.of(0,1);
    private List<CategoriesHelperPojo> categories;
}
