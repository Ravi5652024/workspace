package com.api.coolclub.models.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class GenericRes<T> {
    private Integer statuscode;
    private String status;
    private String message;

    @JsonProperty("data")
    private T data;


    public GenericRes(Integer statuscode, String status, String message) {
        this.statuscode = statuscode;
        this.status = status;
        this.message = message;
    }
}
