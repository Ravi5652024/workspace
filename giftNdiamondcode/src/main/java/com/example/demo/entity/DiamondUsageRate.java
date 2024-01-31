package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
@Document(collection="diamond_usage_rate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondUsageRate {
	
	@Id
	private String id;
//	private String usage_rate;
	private String  operatorId;
	private UsageRate diamondRate;
 
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    public UsageRate getDiamondRate() {
        return diamondRate;
    }

    public void setDiamondRate(UsageRate diamondRate) {
        this.diamondRate = diamondRate;
    }
	
	

}
