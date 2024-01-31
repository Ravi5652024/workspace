package com.api.coolclub.models.request;

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
public class RegisterReq {
    private String userName;
    private String otp;
    private String flowType;
    private String deviceType;
    private String deviceId;
}