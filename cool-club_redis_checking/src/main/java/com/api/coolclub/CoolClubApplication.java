package com.api.coolclub;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CoolClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoolClubApplication.class, args);
	}

	@Value("${connectionTimeOut}")
	private int connectionTimeOut;
    
    @Value("${maxConnTotal}")
	private int maxConnTotal;
    
    @Value("${maxConnPerRoute}")
	private int maxConnPerRoute;
    
    @Value("${socketTimeout}")
	private int socketTimeout;

	@Bean
	public RestTemplate getRestTemplate() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectionTimeOut)
				.setSocketTimeout(socketTimeout)
				.build();
		
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.setMaxConnTotal(maxConnTotal) //max connections in a pool
				.setMaxConnPerRoute(maxConnPerRoute); //max connection per route
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
		
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate = restTemplateBuilder.requestFactory(() -> factory).build();
		
		return restTemplate;
	}
}
