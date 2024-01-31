package com.api.coolclub.models.response;

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
public class GetAllOperatorsRes {
    
    private Integer uuid;
    private String operator;
    private String country;
    private String defaultLanguage;
    private List<String> languageSupport;
    private String cmsUsername;
}