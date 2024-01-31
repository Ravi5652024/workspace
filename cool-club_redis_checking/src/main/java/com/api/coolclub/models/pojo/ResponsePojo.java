package com.api.coolclub.models.pojo;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

/*
 * @Author Rohan_Sharma
*/

public record ResponsePojo(HttpStatus statusCode, JSONObject response) {
    
}
