package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

@Document(collection = "gift_packs")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftDetails {
	@Id
	private String id;
	private String url;
	private float price;
	private String currency;

}