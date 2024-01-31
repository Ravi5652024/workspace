package com.example.demo.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.example.demo.entity.DiamondDetails;
import com.example.demo.entity.DiamondPurchasedHistory;
import com.example.demo.entity.DiamondUsageRate;
import com.example.demo.request.DiamondPurchasedHistoryRequest;
import com.example.demo.request.DiamondRequest;
import com.example.demo.request.DiamondUsageRequest;

public interface DiamondServiceInterface {
	
	
	public  DiamondDetails createDiamondDetails(DiamondRequest diamondRequest);
	public  DiamondDetails updateDiamondDetails(DiamondDetails diamondRequest);
	public DiamondDetails getDiamondDetailsById(DiamondDetails diamondDetails);
	public List<DiamondDetails> getAllDiamondDetails();
	public boolean deleteDiamondDetails(DiamondDetails diamondDetail);
	
	
	
	
	
	
	
	public  DiamondPurchasedHistory saveDiamondPurchasedHistory(DiamondPurchasedHistoryRequest diamondHistoryRequest);

	public  DiamondPurchasedHistory updateDiamondPurchasedHistory(DiamondPurchasedHistoryRequest diamondHistoryRequest);

	public  DiamondPurchasedHistory getDiamondPurchasedHistoryById(DiamondPurchasedHistory diamondHistory);

	public  List<DiamondPurchasedHistory> getAllDiamondPurchasedHistory();

	public  void deleteDiamondPurchasedHistory(DiamondPurchasedHistory diamondHistoryRequest);  
	
	
	
	
	
	
	
	
	

	public DiamondUsageRate saveUsageRate(DiamondUsageRequest diamondUsageRequest);

	public DiamondUsageRate getUsageRateById(DiamondUsageRequest diamondUsageRequest);

	public List<DiamondUsageRate> getAllUsageRate();

    public boolean deleteUsageRate(DiamondUsageRequest diamondUsageRequest);
    
    public DiamondUsageRate updateDiamondUsageRate(DiamondUsageRequest diamondUsageRequest);

}
