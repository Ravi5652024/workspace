package com.example.demo.request;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftRequest {
	@Id
	private String id;
	private String url;
	private float price;
	private String currency;

	
	// Getters and setters
}
