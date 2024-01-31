package com.example.demo.servicesImplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DiamondDetails;
import com.example.demo.entity.GiftDetails;
import com.example.demo.entity.GiftPurchasedHistory;
import com.example.demo.repo.GiftPurchasedHistoryRepo;
import com.example.demo.repo.GiftRepo;
import com.example.demo.request.GiftPurchasedHistoryRequest;
import com.example.demo.request.GiftRequest;
import com.example.demo.response.GiftResponse;
import com.example.demo.service.GiftServiceInterface;

@Service
public class GiftService implements GiftServiceInterface {

	private final GiftRepo giftRepo;
	private final GiftPurchasedHistoryRepo giftHistoryRepo;

	public GiftService(GiftRepo giftRepo,GiftPurchasedHistoryRepo giftHistoryRepo) {
		this.giftRepo = giftRepo;
		this.giftHistoryRepo=giftHistoryRepo;
	}

	

	
	
	
	
	@Override
    public GiftDetails saveGiftDetails(GiftRequest giftRequest) {
        GiftDetails giftDetails = new GiftDetails();
        giftDetails.setUrl(giftRequest.getUrl());
        giftDetails.setPrice(giftRequest.getPrice());
        giftDetails.setCurrency(giftRequest.getCurrency());
//        diamondDetails.setId(diamondRequest.getId());
        giftRepo.save(giftDetails);
        return  giftDetails;    
	}
	
	
	
	
	
	
	
	@Override
    public List<GiftDetails> getAllGiftDetails() {
		List<GiftDetails> giftList = giftRepo.findAll();
		return giftList;
    }
	

	@Override
    public boolean deleteGiftDetails(GiftDetails giftDetails) {
		try {
			String id=giftDetails.getId();
			giftRepo.deleteById(id);
		    return true;
		}
		catch(Exception e) {
			return false;
		}
    }
	
	 @Override
	    public GiftDetails getGiftDetailsById(GiftDetails giftDetails) {
		 String id=giftDetails.getId();
		 
		 GiftDetails d=giftRepo.findById(id).orElse(null);
		 System.out.println("gfhgfh"+id);
		 System.out.println(d);
	        return d;
	    }
	
	 
	 @Override
	 public GiftDetails updateGiftDetails(GiftDetails giftDetails) {
		 GiftDetails existingGiftDetails = giftRepo.findById(giftDetails.getId()).orElse(null);
	     // Update fields based on your logic
		 existingGiftDetails.setPrice(giftDetails.getPrice());
		 existingGiftDetails.setCurrency(giftDetails.getCurrency());
		 existingGiftDetails.setId(giftDetails.getId());
	     giftRepo.save(existingGiftDetails);
	     return existingGiftDetails;
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//
//	@Override
//	public GiftPurchasedHistory saveGiftPurchase(GiftPurchasedHistory giftHistory) {
//		GiftPurchasedHistory newHistory = giftHistoryRepo.save(giftHistory);
//		return newHistory;
//	}
//
//
//
//
//	
	
	
	
	
	
	
	
	
	
	@Override
    public GiftPurchasedHistory saveGiftPurchasedHistory(GiftPurchasedHistoryRequest historyRequest) {
        GiftPurchasedHistory purchasedHistory = new GiftPurchasedHistory();
        purchasedHistory.setGiftId(historyRequest.getGiftId());
        purchasedHistory.setUserId(historyRequest.getUserId());
 
        purchasedHistory.setPurchaseOn(LocalDateTime.now());
        return giftHistoryRepo.save(purchasedHistory);
    }

    @Override
    public GiftPurchasedHistory updateGiftPurchasedHistory(GiftPurchasedHistory request) {
        GiftPurchasedHistory existingPurchasedHistory = giftHistoryRepo.findById(request.getId())
                .orElse(null);

        if (existingPurchasedHistory != null) {
            existingPurchasedHistory.setGiftId(request.getGiftId());
            existingPurchasedHistory.setUserId(request.getUserId());
   
            existingPurchasedHistory.setPurchaseOn(LocalDateTime.now());

            return giftHistoryRepo.save(existingPurchasedHistory);
        } else {
            return null; // Or handle the case where the record is not found
        }
    }

    @Override
    public GiftPurchasedHistory getGiftPurchasedHistoryById(GiftPurchasedHistory request) {
        return giftHistoryRepo.findById(request.getId()).orElse(null);
    }

    @Override
    public List<GiftPurchasedHistory> getAllGiftPurchasedHistory() {
        return giftHistoryRepo.findAll();
    }

    @Override
    public boolean deleteGiftPurchasedHistory(GiftPurchasedHistory request) {
        try {
            String id = request.getId();
            giftHistoryRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}