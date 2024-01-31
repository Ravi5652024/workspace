package com.example.demo.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftResponse {
	private String id;
	private String url;
	private float price;
	private String currency;

	

}