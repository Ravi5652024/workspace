package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.GiftDetails;
import com.example.demo.entity.GiftPurchasedHistory;
import com.example.demo.request.GiftPurchasedHistoryRequest;
import com.example.demo.request.GiftRequest;
import com.example.demo.response.GiftResponse;

public interface GiftServiceInterface {

	
	
	
	
	
	
	public GiftDetails saveGiftDetails(GiftRequest giftRequest);

	public GiftDetails updateGiftDetails(GiftDetails giftDetails);
		
    public GiftDetails getGiftDetailsById(GiftDetails giftDetails);
		
    public List<GiftDetails> getAllGiftDetails();

    public boolean deleteGiftDetails(GiftDetails giftDetails);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	public GiftPurchasedHistory saveGiftPurchase(GiftPurchasedHistory giftHistory);
//
//	
//	
	
	
	
	
	
	
	
	
	
	public GiftPurchasedHistory saveGiftPurchasedHistory(GiftPurchasedHistoryRequest historyRequest);

	public GiftPurchasedHistory updateGiftPurchasedHistory(GiftPurchasedHistory Request);
		
    public GiftPurchasedHistory getGiftPurchasedHistoryById(GiftPurchasedHistory hRequest);
		
    public List<GiftPurchasedHistory> getAllGiftPurchasedHistory();

    public boolean deleteGiftPurchasedHistory(GiftPurchasedHistory Request);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
