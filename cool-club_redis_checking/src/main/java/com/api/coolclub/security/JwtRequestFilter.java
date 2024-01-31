package com.api.coolclub.security;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.utils.Constants;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @Author Rohan_Sharma
*/

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final Gson gson;
    private final JwtUtil jwtUtil;
    public JwtRequestFilter(Gson gson, JwtUtil jwtUtil){
        this.gson = gson;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        
        GenericRes<String> genralResponse;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try{
                username = jwtUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e) {
                log.error("-- ERROR : UNABLE TO GET ACCESS TOKEN");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                genralResponse = new GenericRes<>(Constants.UNAUTHORIZED_CODE, Constants.UNSUCCESSFUL, Constants.UNAUTHORIZED_REQUEST);
                response.getWriter().write(gson.toJson(genralResponse));
                return;
            } catch (ExpiredJwtException e) {
                log.error("-- ERROR : ACCESS TOKEN EXPIRED");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                genralResponse = new GenericRes<>(Constants.FORBIDDEN_CODE, Constants.UNSUCCESSFUL, Constants.JWT_TOKEN_EXPIRED);
                response.getWriter().write(gson.toJson(genralResponse));
                return;
            } 
            catch (Exception e) {
                log.error("-- ERROR : INVALID TOKEN");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                genralResponse = new GenericRes<>(Constants.UNAUTHORIZED_CODE, Constants.UNSUCCESSFUL, Constants.UNAUTHORIZED_REQUEST);
                response.getWriter().write(gson.toJson(genralResponse));
                return;
            }
        }else{
            //-- header not found
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = new User(username, username, new ArrayList<>());
            if(Boolean.TRUE.equals(jwtUtil.validateToken(jwtToken, username))){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                log.error("-- ERROR : INVALID TOKEN");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                genralResponse = new GenericRes<>(Constants.UNAUTHORIZED_CODE, Constants.UNSUCCESSFUL, Constants.INVALID_JWT_TOKEN);
                response.getWriter().write(gson.toJson(genralResponse));
                return;
            }
        }

        if(username != null){
            //-- if everything is fine then check user is authorized of not
            // UserStatusEnum status = userRepository.findUserStatus(username);
            // log.debug("-- authUser status: {}",status);
            // if(status == null || !status.equals(UserStatusEnum.ACTIVE)){
            //     log.warn("-- auth user not found or not active : username:{} ",username);
            //     ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //     response.setContentType("application/json");
            //     genralResponse = new GeneralRes(UNAUTHORIZED_CODE, FAILURE, UNAUTHORIZED_USER);
            //     response.getWriter().write(gson.toJson(genralResponse));
            //     return;
            // }
        }
        
        filterChain.doFilter(request, response);
    }
}
