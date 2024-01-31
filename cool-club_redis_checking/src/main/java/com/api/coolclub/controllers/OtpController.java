package com.api.coolclub.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.services.OtpService;
import com.api.coolclub.utils.HelperUtil;
import com.api.coolclub.utils.URIConstants;
import com.google.gson.Gson;


/*
 * @Author Rohan_Sharma
*/

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URIConstants.API_VERSION+URIConstants.OTP_BASE_URL)
public class OtpController {
    private static final Logger log = LoggerFactory.getLogger(OtpController.class);

    private final Gson gson;
    private final OtpService otpService;
    private final HelperUtil helperUtil;
    public OtpController(Gson gson, OtpService otpService, HelperUtil helperUtil){
        this.gson = gson;
        this.otpService = otpService;
        this.helperUtil = helperUtil;
    }

    /* ---------------------------------- SEND OTP ----------------------------------- */
    @GetMapping(URIConstants.OTP_SEND)
    public ResponseEntity<GenericRes<String>> sendOTP(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @PathVariable("userName") String userName) {
        lang = helperUtil.checkLanguageSupport(lang, operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        
        log.info("## [sendOTP]-{}: request - lang: {}, operatorId: {}",userName, lang, operatorId);
        ResponseEntity<GenericRes<String>> response = otpService.sendOTP(userName, lang, operatorId);
        log.info("-- [sendOTP]-{}: response - {}",userName,gson.toJson(response));
        return response;
    }
    
}
