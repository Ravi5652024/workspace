package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="diamond_packs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondDetails {
	@Id
//	private String Id;
	private String id;
	private String url;
	private int diamondCount;
	private float price;
	private String currency;


}
