package com.api.coolclub.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author Rohan_Sharma
*/

@Document(collection = "operator_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorConfig {
    @Id
    private String id;
    private Integer uuid;
    @Indexed
    private String operator;
    @Indexed
    private String country;
    private String defaultLanguage;
    private List<String> languageSupport;

    private String cmsUsername;
    private String cmsPassword;

    public OperatorConfig(String operator, String country, String defaultLanguage, List<String> languageSupport, String cmsUsername, String cmsPassword) {
        this.operator = operator;
        this.country = country;
        this.defaultLanguage = defaultLanguage;
        this.languageSupport = languageSupport;
        this.cmsUsername = cmsUsername;
        this.cmsPassword = cmsPassword;
    }
}
