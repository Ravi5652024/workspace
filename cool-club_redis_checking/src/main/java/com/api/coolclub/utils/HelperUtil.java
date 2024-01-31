package com.api.coolclub.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.enums.GenderEnum;
import com.api.coolclub.models.pojo.OperatorsDetailsPojo;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.security.JwtUtil;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Component
public class HelperUtil {
    private static final Logger log = LoggerFactory.getLogger(HelperUtil.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );
    

    private final Gson gson;
    private final JwtUtil jwtUtil;
    public HelperUtil(Gson gson, JwtUtil jwtUtil){
        this.gson = gson;
        this.jwtUtil = jwtUtil;
    }

    /* --------------------------------------- JWT HELPER --------------------------------------- */
    public String getUserNameFromJwt(String authorizationHeader){
        String jwtToken = authorizationHeader.substring(7);
        return jwtUtil.getUsernameFromToken(jwtToken);
    }

    /* --------------------------------------- GENERATE TOKEN --------------------------------------- */
    public String generateToken(String userName){
        return jwtUtil.generateToken(userName);
    }

    /* --------------------------------------- Language Check --------------------------------------- */
    public String checkLanguageSupport(String userLang, Integer operatorId){
        OperatorsDetailsPojo operatorDetails = GeneralConfig.operatorsDetails.get(operatorId);

        if(operatorDetails == null){
            return null;
        }
        String defaultLanguage = operatorDetails.getDefaultLanguage();

        return operatorDetails.getLanguageSupport().stream()
            .filter(lang -> lang.trim().equalsIgnoreCase(userLang))
            .findFirst()
            .orElse(defaultLanguage);
    }

    /* --------------------------------------- GENERAL RESPONSE FOR INVALID OPERATORID--------------------------------------- */
    public <T> ResponseEntity<GenericRes<T>> getInvalidOperatorError(){
        log.info("#### INVALID OPERATORID ####");
        String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_OPERATOR_ID).get("en");
        return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------- GET GENDER ENUM --------------------------------------- */
    public GenderEnum getGenderEnumFromString(String selectedGender, String lang){
        int index = -1;
        List<String> allGenders = GeneralConfig.gendersMap.get(lang);
        for (String gender : allGenders) {
            if (gender.equalsIgnoreCase(selectedGender)) {
                index = allGenders.indexOf(gender);
                break;
            }
        }
        return getGenderEnum(index);
    }

    public GenderEnum getGenderEnum(int index){
        switch (index) {
            case 0:
                return GenderEnum.MALE;
            case 1:
                return GenderEnum.FEMALE;
            case 2:
                return GenderEnum.NON_BINARY;
            default:
                return null;
        }
    }

    /* --------------------------------------- GET GENDER STRING --------------------------------------- */
    public String getGenderString(GenderEnum gender, String lang) {
        if(gender != null){
            int index = 0;
            if(gender.equals(GenderEnum.MALE)){
                index = 0;
            }else if(gender.equals(GenderEnum.FEMALE)){
                index = 1;
            }else{
                index = 2;
            }
    
            List<String> allGenders = GeneralConfig.gendersMap.get(lang);
            return allGenders.get(index);
        }else{
            return null;
        }
    }

    /* --------------------------------------- IS EMAIL CHECK --------------------------------------- */
    public Boolean isEmail(String userName){
        Matcher matcher = EMAIL_PATTERN.matcher(userName);
        return matcher.matches();
    }

    /* --------------------------------------- GENERATE OTP --------------------------------------- */
    public String generateOTP(int len){
        String numbers = "0123456789";
        Random rnd = new Random();
        char[] otpArr = new char[len];
        for(int i=0;i<len;i++) {
            otpArr[i] =  numbers.charAt(rnd.nextInt(numbers.length()));
        }
        return new String(otpArr);
    }
}
