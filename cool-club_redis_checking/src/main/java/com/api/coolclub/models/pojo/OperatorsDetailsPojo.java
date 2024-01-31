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
public class OperatorsDetailsPojo {
    private String defaultLanguage;
    private List<String> languageSupport;
    private String jwt;
}
