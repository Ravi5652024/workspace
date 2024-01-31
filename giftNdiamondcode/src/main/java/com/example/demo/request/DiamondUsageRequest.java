package com.example.demo.request;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondUsageRequest {
	
	private String id;
    private Double callRate;
    private Double imageRate;
    private String operatorId;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
