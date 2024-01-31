package com.api.coolclub.utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.api.coolclub.models.pojo.ResponsePojo;

/*
 * @Author Rohan_Sharma
*/

@Component
public class APICallHandlerUtil {
    private static final Logger log = LoggerFactory.getLogger(APICallHandlerUtil.class);

    /* --------------------------------------- GET API HANDLER --------------------------------------- */
    public static ResponsePojo hitGetAPI(String authUser, String url, RestTemplate restTemplate, String jwt){
        try {
            ResponseEntity<String> apiResponse = null;
            if(jwt != null){
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + jwt);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                apiResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            }else{
                apiResponse = restTemplate.exchange(url, HttpMethod.GET,null,String.class);
            }
           
            HttpStatus statusCode = apiResponse.getStatusCode();
            String responseBody = apiResponse.getBody();

            return new ResponsePojo(statusCode, new JSONObject(responseBody));
        } catch (Exception e) {
            log.error("-- [hitGetAPI]-{} : ERROR",authUser, e);
            return null;
        }
    }

    /* ----------------------------------- POST API HANDLER (with json body) ---------------------------------- */
    public static ResponsePojo hitPostAPI(String authUser,String url, String body, RestTemplate restTemplate, String jwt){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = null;
            ResponseEntity<String> apiResponse = null;

            if(jwt != null){
                headers.set("Authorization", "Bearer "+jwt);
                requestEntity = new HttpEntity<>(body, headers);
                apiResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            }else{
                requestEntity = new HttpEntity<>(body, headers);
                apiResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            }
            
            HttpStatus statusCode = apiResponse.getStatusCode();
            String responseBody = apiResponse.getBody();

            return new ResponsePojo(statusCode, new JSONObject(responseBody));
        } catch (Exception e) {
            log.error("-- [hitPostAPI]-{} : ERROR",authUser, e);
            return null;
        }
    }
}
