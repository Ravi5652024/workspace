package com.example.demo.response;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiamondResponse {
	
	
	@Id
	private String Id;
	private String url;
	private int diamondCount;
	private float price;
	private String currency;

}
