package com.api.coolclub.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.utils.Constants;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @Author Rohan_Sharma
*/

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    
    private Gson gson;
    public JwtAuthenticationEntryPoint(Gson gson){
        this.gson = gson;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
       
        log.error("-- ERROR : [JwtAuthenticationEntryPoint]: {}", authException.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        GenericRes<String> res = new GenericRes<>(Constants.UNAUTHORIZED_CODE, Constants.UNSUCCESSFUL, Constants.UNAUTHORIZED_REQUEST);
        response.getOutputStream().println(gson.toJson(res));
    }
}
