package com.api.coolclub.models.response;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.api.coolclub.models.pojo.CategoriesPojo;

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
public class GetCategoriesRes {
    private List<CategoriesPojo> json;
}
