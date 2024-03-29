package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DiamondDetails;
import com.example.demo.entity.DiamondPurchasedHistory;
import com.example.demo.entity.DiamondUsageRate;

import com.example.demo.request.DiamondPurchasedHistoryRequest;
import com.example.demo.request.DiamondRequest;
import com.example.demo.request.DiamondUsageRequest;
import com.example.demo.response.DiamondResponse;
import com.example.demo.response.GenericRes;
import com.example.demo.service.DiamondServiceInterface;

import com.example.demo.utils.URLConstants;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URLConstants.API_VERSION + URLConstants.DIAMOND)

public class DiamondController {



	private final DiamondServiceInterface diamondServiceInterafce;
	


	public DiamondController(DiamondServiceInterface diamondServiceInterafce) {
		this.diamondServiceInterafce = diamondServiceInterafce;
		

	}

	
	

	 @PostMapping(URLConstants.Save_DIAMONDS_PACK)
	    public ResponseEntity<GenericRes<DiamondDetails>> createDiamondDetails(@RequestBody DiamondRequest diamondRequest) {
	        DiamondDetails diamondDetails = diamondServiceInterafce.createDiamondDetails(diamondRequest);

	        GenericRes<DiamondDetails> response = new GenericRes<>(200, "OK", "DIAMOND_DETAILS_CREATED", diamondDetails);
	        return ResponseEntity.ok(response);
	    }
	 
	 
	 
	 
	 @GetMapping(URLConstants.GET_DIAMONDS_PACK)
	    public ResponseEntity<GenericRes<DiamondDetails>> getDiamondDetailsById(@RequestBody DiamondDetails diamondDetail) {
	        DiamondDetails diamondDetails = diamondServiceInterafce.getDiamondDetailsById(diamondDetail);

	        if (diamondDetails != null) {
	            GenericRes<DiamondDetails> response = new GenericRes<>(200, "OK", "DIAMOND_DETAILS_FOUND", diamondDetails);
	            return ResponseEntity.ok(response);
	        } else {
	            GenericRes<DiamondDetails> response = new GenericRes<>(404, "NOT_FOUND", "DIAMOND_DETAILS_NOT_FOUND", null);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	 
	 
	 
	 @GetMapping(URLConstants.GET_ALL_DIAMONDS_PACKS)
	    public ResponseEntity<List<GenericRes<DiamondDetails>>> getAllDiamondDetails() {
	        List<DiamondDetails> diamondDetailsList = diamondServiceInterafce.getAllDiamondDetails();

	        List<GenericRes<DiamondDetails>> responseList = diamondDetailsList.stream()
	                .map(diamondDetails -> new GenericRes<>(200, "OK", "DIAMOND_DETAILS_FOUND", diamondDetails))
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(responseList);
	    }
	 
	 

	 @PutMapping(URLConstants.UPDATE_DIAMONDS_PACK)
	    public ResponseEntity<GenericRes<DiamondDetails>> updateDiamondDetails(
	    		@RequestBody DiamondDetails diamondRequest) {
	        DiamondDetails updatedDiamondDetails = diamondServiceInterafce.updateDiamondDetails(diamondRequest);

	        if (updatedDiamondDetails != null) {
	            GenericRes<DiamondDetails> response = new GenericRes<>(200, "OK", "DIAMOND_DETAILS_UPDATED", updatedDiamondDetails);
	            return ResponseEntity.ok(response);
	        } else {
	            GenericRes<DiamondDetails> response = new GenericRes<>(404, "NOT_FOUND", "DIAMOND_DETAILS_NOT_FOUND", null);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }

	 
	 
	 
	 
	 
	 @DeleteMapping(URLConstants.DELETE_DIAMONDS_PACK)
	    public ResponseEntity<GenericRes> deleteDiamondDetails(@RequestBody DiamondDetails diamondDetail) {
	        boolean isDeleted = diamondServiceInterafce.deleteDiamondDetails(diamondDetail);

	        if (isDeleted) {
	            GenericRes response = new GenericRes<>(200, "OK", "DIAMOND_DETAILS_DELETED", "Deleted");
	            return ResponseEntity.ok(response);
	        } else {
	            GenericRes response = new GenericRes<>(404, "NOT_FOUND", "DIAMOND_DETAILS_NOT_FOUND", "Not Found");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	 
	
	 
	 
	 
	 
	 
	//Done checking
	
	
	
	//Here remaing testing
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping(URLConstants.SAVE_DIAMONDS_PACK_PURCHASE)
    public ResponseEntity<GenericRes<DiamondPurchasedHistory>> saveDiamondPurchasedHistory(
            @RequestBody DiamondPurchasedHistoryRequest diamondHistoryRequest) {
        DiamondPurchasedHistory savedHistory = diamondServiceInterafce.saveDiamondPurchasedHistory(diamondHistoryRequest);
        GenericRes<DiamondPurchasedHistory> response = new GenericRes<>(200, "OK", "SAVED", savedHistory);
        return ResponseEntity.ok(response);
    }

    @PutMapping(URLConstants.UPDATE_DIAMONDS_PACK_PURCHASE)
    public ResponseEntity<GenericRes<DiamondPurchasedHistory>> updateDiamondPurchasedHistory(
                 @RequestBody DiamondPurchasedHistoryRequest diamondHistoryRequest) {
        DiamondPurchasedHistory updatedHistory = diamondServiceInterafce.updateDiamondPurchasedHistory(diamondHistoryRequest);
        GenericRes<DiamondPurchasedHistory> response = new GenericRes<>(200, "OK", "UPDATED", updatedHistory);
        return ResponseEntity.ok(response);
    }

    @GetMapping(URLConstants.GET_DIAMONDS_PACK_PURCHASE)
    public ResponseEntity<GenericRes<DiamondPurchasedHistory>> getDiamondPurchasedHistoryById(@RequestBody DiamondPurchasedHistory diamondHistoryRequest) {
        DiamondPurchasedHistory history = diamondServiceInterafce.getDiamondPurchasedHistoryById(diamondHistoryRequest);
        System.out.println(history);
        GenericRes<DiamondPurchasedHistory> response = new GenericRes<>(200, "OK", "FOUND", history);
        return ResponseEntity.ok(response);
    }

    @GetMapping(URLConstants.GET_ALL_DIAMONDS_PACK_PURCHASE)
    public ResponseEntity<GenericRes<List<DiamondPurchasedHistory>>> getAllDiamondPurchasedHistory() {
        List<DiamondPurchasedHistory> historyList = diamondServiceInterafce.getAllDiamondPurchasedHistory();
        GenericRes<List<DiamondPurchasedHistory>> response = new GenericRes<>(200, "OK", "FOUND_ALL", historyList);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(URLConstants.DELETE_DIAMONDS_PACK_PURCHASE)
    public ResponseEntity<GenericRes> deleteDiamondPurchasedHistory(@RequestBody DiamondPurchasedHistory diamondHistoryRequest) {
    	diamondServiceInterafce.deleteDiamondPurchasedHistory(diamondHistoryRequest);
        GenericRes response = new GenericRes<>(200, "OK", "DELETED", "Object");
        return ResponseEntity.ok(response);
    }
	
	
    
    
    
    
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    @PostMapping(URLConstants.Save_DIAMONDS_USAGE_RATE)
    public ResponseEntity<GenericRes<DiamondUsageRate>> saveUsageRate(@RequestBody DiamondUsageRequest diamondUsageRequest) {
    	DiamondUsageRate savedDiamond = diamondServiceInterafce.saveUsageRate(diamondUsageRequest);
        GenericRes<DiamondUsageRate> response = new GenericRes<>(200, "OK", "DIAMOND_SAVED", savedDiamond);
        return ResponseEntity.ok(response);
    }

    @GetMapping(URLConstants.GET_DIAMONDS_USAGE_RATE)
    public ResponseEntity<GenericRes<DiamondUsageRate>> getDiamondById(@RequestBody DiamondUsageRequest diamondUsageRequest) {
        // Assuming you have a method like getDiamondUsageRateById in your service
    	
        DiamondUsageRate diamondUsageRate = diamondServiceInterafce.getUsageRateById(diamondUsageRequest);
        
        if (diamondUsageRate != null) {
            GenericRes<DiamondUsageRate> response = new GenericRes<>(200, "OK", "DIAMOND_FOUND", diamondUsageRate);
            return ResponseEntity.ok(response);
        } else {
          
            GenericRes<DiamondUsageRate> response = new GenericRes<>(404, "Not Found", "DIAMOND_NOT_FOUND", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @GetMapping(URLConstants.GET_ALL_DIAMONDS_USAGE_RATE)
    public ResponseEntity<GenericRes<List<DiamondUsageRate>>> getAllUsageRate() {
        List<DiamondUsageRate> diamonds = diamondServiceInterafce.getAllUsageRate();
        GenericRes<List<DiamondUsageRate>> response = new GenericRes<>(200, "OK", "DIAMONDS_FOUND", diamonds);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(URLConstants.DELETE_DIAMONDS_USAGE_RATE)
    public ResponseEntity<GenericRes> deleteUsageRate(@RequestBody DiamondUsageRequest diamondUsageRequest) {
    	
        boolean deleted = diamondServiceInterafce.deleteUsageRate(diamondUsageRequest);

        if (deleted) {
            GenericRes response = new GenericRes<>(200, "OK", "Diamond Usage Rate_deleted", "Object");
            return ResponseEntity.ok(response);
        } else {
            // Handle case when the diamond with the given ID is not found
            GenericRes response = new GenericRes<>(404, "Not Found", "Usage Rate with given Id_NOT_FOUND", "object");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
	
	
	
	//update
    
    @PutMapping(URLConstants.UPDATE_DIAMONDS_USAGE_RATE)
    public ResponseEntity<GenericRes<DiamondUsageRate>> updateDiamondUsageRate(
                 @RequestBody  DiamondUsageRequest diamondUsageRequest) {
    	DiamondUsageRate diamondUsageRate = diamondServiceInterafce.updateDiamondUsageRate(diamondUsageRequest);
        GenericRes<DiamondUsageRate> response = new GenericRes<>(200, "OK", "UPDATED", diamondUsageRate);
        return ResponseEntity.ok(response);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
