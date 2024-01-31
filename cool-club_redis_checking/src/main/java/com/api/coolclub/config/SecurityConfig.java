package com.api.coolclub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.api.coolclub.security.JwtAuthenticationEntryPoint;
import com.api.coolclub.security.JwtRequestFilter;

/*
 * @Author Rohan_Sharma
*/

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    public SecurityConfig(JwtRequestFilter jwtRequestFilter,JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint){
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf()
            .disable()
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers("/v1/**/**/**").permitAll()
            .antMatchers("/refresh/**","/v1/api/**/**","/v1/otp/**").permitAll()
            .antMatchers("/v1/user/register","/v1/user/login").permitAll()
            .antMatchers("/v2/api-docs","/swagger-resources/**","/swagger-ui/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
