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
public class GiftPurchasedHistoryRequest {

	@Id
	private String id;
	private String giftId;
	private String userId;
	private String currency;
	private float price;

	
}
