package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "diamond_purchased_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondPurchasedHistory {

	@Id
	private String id; // ObjectId
	private String diamondPackId;
	private String userId;
	private int diamondCount;
	private float price;

	private LocalDateTime purchaseOn;
}