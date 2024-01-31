package com.api.coolclub.models.request;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateOperatorReq {

    private Integer uuid;
    private String name;
    private String country;
    private String defaultLanguage;
    private List<String> languageSupport;
    private String cmsUsername;
    private String cmsPassword;

}
