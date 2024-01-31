package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "gift_purchased_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftPurchasedHistory {

	@Id
	private String id; // ObjectId
	private String giftId;
	private String userId;
	private LocalDateTime purchaseOn;

}