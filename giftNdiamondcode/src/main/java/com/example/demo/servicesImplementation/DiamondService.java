package com.example.demo.servicesImplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DiamondDetails;
import com.example.demo.entity.DiamondPurchasedHistory;
import com.example.demo.entity.UsageRate;
import com.example.demo.entity.DiamondUsageRate;
import com.example.demo.repo.DiamondPurchasedHistoryRepo;
import com.example.demo.repo.DiamondRepo;
import com.example.demo.repo.DiamondUsageRateRepo;
import com.example.demo.request.DiamondPurchasedHistoryRequest;
import com.example.demo.request.DiamondRequest;
import com.example.demo.request.DiamondUsageRequest;
import com.example.demo.service.DiamondServiceInterface;

@Service
public class DiamondService implements DiamondServiceInterface {

	
	private final DiamondRepo diamondRepo;
	private final DiamondPurchasedHistoryRepo diamondHistoryRepo;
	
	 private final  DiamondUsageRateRepo diamondUsageRateRepo;
	
	public DiamondService(DiamondRepo diamondRepo,DiamondPurchasedHistoryRepo diamondHistoryRepo,DiamondUsageRateRepo diamondUsageRateRepo){
		this.diamondRepo=diamondRepo;
		this.diamondHistoryRepo=diamondHistoryRepo;
		this.diamondUsageRateRepo=diamondUsageRateRepo;
	}
	
	
	
	@Override
    public DiamondDetails createDiamondDetails(DiamondRequest diamondRequest) {
        DiamondDetails diamondDetails = new DiamondDetails();
        diamondDetails.setUrl(diamondRequest.getUrl());
        diamondDetails.setDiamondCount(diamondRequest.getDiamondCount());
        diamondDetails.setPrice(diamondRequest.getPrice());
        diamondDetails.setCurrency(diamondRequest.getCurrency());
//        diamondDetails.setId(diamondRequest.getId());
              diamondRepo.save(diamondDetails);
        return  diamondDetails;     
    }

	
	
	@Override
    public List<DiamondDetails> getAllDiamondDetails() {
		List<DiamondDetails> packList = diamondRepo.findAll();
		return packList;
    }
	

	@Override
    public boolean deleteDiamondDetails(DiamondDetails diamondDetail) {
		try {
			String id=diamondDetail.getId();
		    diamondRepo.deleteById(id);
		    return true;
		}
		catch(Exception e) {
			return false;
		}
    }
	
	 @Override
	    public DiamondDetails getDiamondDetailsById(DiamondDetails diamondDetails) {
		 String id=diamondDetails.getId();
		 
		 DiamondDetails d=diamondRepo.findById(id).orElse(null);
		 System.out.println("gfhgfh"+id);
		 System.out.println(d);
	        return d;
	    }
	
	 
	 @Override
	 public DiamondDetails updateDiamondDetails(DiamondDetails diamondRequest) {
	     DiamondDetails existingDiamondDetails = diamondRepo.findById(diamondRequest.getId()).orElse(null);
	     // Update fields based on your logic
	     existingDiamondDetails.setDiamondCount(diamondRequest.getDiamondCount());
	     existingDiamondDetails.setPrice(diamondRequest.getPrice());
	     existingDiamondDetails.setCurrency(diamondRequest.getCurrency());
	     
	     existingDiamondDetails.setId(diamondRequest.getId());
	     return diamondRepo.save(existingDiamondDetails);
	 }
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
    public DiamondPurchasedHistory saveDiamondPurchasedHistory(DiamondPurchasedHistoryRequest diamondHistoryRequest) {
        DiamondPurchasedHistory purchasedHistory = new DiamondPurchasedHistory();
        purchasedHistory.setDiamondPackId(diamondHistoryRequest.getDiamondPackId());
        purchasedHistory.setUserId(diamondHistoryRequest.getUserId());
        purchasedHistory.setDiamondCount(diamondHistoryRequest.getDiamondCount());
        purchasedHistory.setPrice(diamondHistoryRequest.getPrice());
        purchasedHistory.setPurchaseOn(LocalDateTime.now());
        return diamondHistoryRepo.save(purchasedHistory);
    }

    @Override
    public DiamondPurchasedHistory updateDiamondPurchasedHistory(DiamondPurchasedHistoryRequest diamondHistoryRequest) {
    	String id=diamondHistoryRequest.getId();
        DiamondPurchasedHistory existingPurchasedHistory = diamondHistoryRepo.findById(id).get();

        existingPurchasedHistory.setDiamondPackId(diamondHistoryRequest.getDiamondPackId());
        existingPurchasedHistory.setUserId(diamondHistoryRequest.getUserId());
        existingPurchasedHistory.setDiamondCount(diamondHistoryRequest.getDiamondCount());
        existingPurchasedHistory.setPurchaseOn(LocalDateTime.now());
        existingPurchasedHistory.setPrice(diamondHistoryRequest.getPrice());
        existingPurchasedHistory.setId(diamondHistoryRequest.getId());
        
        return diamondHistoryRepo.save(existingPurchasedHistory);
    }

    @Override
    public DiamondPurchasedHistory getDiamondPurchasedHistoryById(DiamondPurchasedHistory diamondHistoryRequest) {
    	String id=diamondHistoryRequest.getId();
    	DiamondPurchasedHistory d=diamondHistoryRepo.findById(id).get();
    	System.out.println("gfhgfh"+id);
    	System.out.println(d);
        return d;
        
      
    }

    @Override
    public List<DiamondPurchasedHistory> getAllDiamondPurchasedHistory() {
        return diamondHistoryRepo.findAll();
    }

    @Override
    public void deleteDiamondPurchasedHistory(DiamondPurchasedHistory diamondHistoryRequest) {
    	String id=diamondHistoryRequest.getId();
    	diamondHistoryRepo.deleteById(id);
    	
    }
	
	
	
	
	
	
	
	
	
	
	
	
    
    
	
	
	
	
	
	
	
	
   

    @Override
    public DiamondUsageRate saveUsageRate(DiamondUsageRequest diamondRequest) {
        DiamondUsageRate diamondUsageRate = new DiamondUsageRate();
        diamondUsageRate.setDiamondRate(new UsageRate(diamondRequest.getCallRate(), diamondRequest.getImageRate()));
        diamondUsageRate.setOperatorId(diamondRequest.getOperatorId());
        diamondUsageRate.setCreatedOn(LocalDateTime.now());
        diamondUsageRate.setUpdatedOn(LocalDateTime.now());
        diamondUsageRate.setId(diamondRequest.getId());
        diamondUsageRateRepo.save(diamondUsageRate);
        return  diamondUsageRate;
    }

    @Override
    public DiamondUsageRate getUsageRateById(DiamondUsageRequest diamondUsageRequest) {
    	String id=diamondUsageRequest.getId();
        return diamondUsageRateRepo.findById(id).orElse(null);
    }

    @Override
    public List<DiamondUsageRate> getAllUsageRate() {
    	List<DiamondUsageRate> usagelist=diamondUsageRateRepo.findAll();
    	return usagelist;
    }

    @Override
    public boolean deleteUsageRate(DiamondUsageRequest diamondUsageRequest) {
    	try {
    		String id=diamondUsageRequest.getId();
    	diamondUsageRateRepo.deleteById(id);
    	return true;
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    }
    
    
    
    @Override
    public DiamondUsageRate updateDiamondUsageRate(DiamondUsageRequest diamondUsageRequest) {
    	String id=diamondUsageRequest.getId();
        DiamondUsageRate existingDiamondUsageRate = diamondUsageRateRepo.findById(id).orElse(null);
       
//        if (existingDiamondUsageRate.getId()==diamondUsageRequest.getId()) {
//        	existingDiamondUsageRate.setDiamondRate(new UsageRate(diamondUsageRequest.getCallRate(), diamondUsageRequest.getImageRate()));
//        	existingDiamondUsageRate.setOperatorId(diamondUsageRequest.getOperatorId());
//        	existingDiamondUsageRate.setCreatedOn(LocalDateTime.now());
//        	existingDiamondUsageRate.setUpdatedOn(LocalDateTime.now());
//        	existingDiamondUsageRate.setId(diamondUsageRequest.getId());
//            diamondUsageRateRepo.save(existingDiamondUsageRate);
//            return  existingDiamondUsageRate;
//        }
        existingDiamondUsageRate.setDiamondRate(new UsageRate(diamondUsageRequest.getCallRate(), diamondUsageRequest.getImageRate()));
    	existingDiamondUsageRate.setOperatorId(diamondUsageRequest.getOperatorId());
    	existingDiamondUsageRate.setCreatedOn(LocalDateTime.now());
    	existingDiamondUsageRate.setUpdatedOn(LocalDateTime.now());
    	existingDiamondUsageRate.setId(diamondUsageRequest.getId());
        diamondUsageRateRepo.save(existingDiamondUsageRate);
        return  existingDiamondUsageRate;
        
        
    }


	

}
