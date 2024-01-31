package com.api.coolclub.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * @Author Rohan_Sharma
*/

@Component
public class JwtUtil {
    
    @Value("${jwtExpirationTime}")
    private long jwtExpirationTime;

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;
    
    //-- retrive username from token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    //-- retrive expireDate from token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    //-- for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }
    
    //-- check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //-- generate token for user
    public String generateToken(String userNamae) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userNamae);
    }

    //-- generate token for user
    public String refreshToken(String userNamae) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userNamae);
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();
    }

    //-- validate token
    public Boolean validateToken(String token, String userEamil) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userEamil) && !isTokenExpired(token));
    }
}
