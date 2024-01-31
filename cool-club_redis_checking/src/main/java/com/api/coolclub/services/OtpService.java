package com.api.coolclub.services;

import org.springframework.http.ResponseEntity;

import com.api.coolclub.models.response.GenericRes;

/*
 * @Author Rohan_Sharma
*/

public interface OtpService {
    
    /*
	 * SEND OTP
     * @params ()
	*/
    public ResponseEntity<GenericRes<String>> sendOTP(String userName, String lang, Integer operatorId);
}
