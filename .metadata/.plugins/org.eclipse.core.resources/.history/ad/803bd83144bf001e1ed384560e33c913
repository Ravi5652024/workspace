package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DiamondDetails;
import com.example.demo.entity.GiftDetails;
import com.example.demo.entity.GiftPurchasedHistory;

import com.example.demo.request.DiamondRequest;
import com.example.demo.request.GiftPurchasedHistoryRequest;
import com.example.demo.request.GiftRequest;
import com.example.demo.response.GenericRes;
import com.example.demo.response.GiftResponse;
import com.example.demo.service.GiftServiceInterface;

import com.example.demo.utils.URLConstants;

@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URLConstants.API_VERSION + URLConstants.GIFT)

public class GiftController {

	private final GiftServiceInterface giftServiceInterface;

	public GiftController(GiftServiceInterface giftServiceInterface) {
		this.giftServiceInterface = giftServiceInterface;
		
	}

	
	
	
	
	
	@PostMapping(URLConstants.Save_GIFT)
    public ResponseEntity<GenericRes<GiftDetails>> saveGiftDetails(@RequestBody GiftRequest giftRequest) {
		GiftDetails newDetails = giftServiceInterface.saveGiftDetails(giftRequest);

        GenericRes<GiftDetails> response = new GenericRes<>(200, "OK", "DIAMOND_DETAILS_CREATED", newDetails);
        return ResponseEntity.ok(response);
    }
 
 
 
 
 @GetMapping(URLConstants.GET_GIFT)
    public ResponseEntity<GenericRes<GiftDetails>> getGiftDetailsById(@RequestBody GiftDetails giftDetail) {
	    GiftDetails newDetails = giftServiceInterface.getGiftDetailsById(giftDetail);

        if (newDetails != null) {
            GenericRes<GiftDetails> response = new GenericRes<>(200, "OK", "GIFT_DETAILS_FOUND", newDetails);
            return ResponseEntity.ok(response);
        } else {
            GenericRes<GiftDetails> response = new GenericRes<>(404, "NOT_FOUND", "GIFT_DETAILS_NOT_FOUND", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
 
 
 
 @GetMapping(URLConstants.GET_ALL_GIFTS)
    public ResponseEntity<List<GenericRes<GiftDetails>>> getAllGiftDetails() {
        List<GiftDetails> giftDetailsList = giftServiceInterface.getAllGiftDetails();

        List<GenericRes<GiftDetails>> responseList = giftDetailsList.stream()
                .map(giftDetails -> new GenericRes<>(200, "OK", "GIFT_DETAILS_FOUND", giftDetails))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
 
 

 @PutMapping(URLConstants.UPDATE_GIFT)
    public ResponseEntity<GenericRes<GiftDetails>> updateGiftDetails(
    		@RequestBody GiftDetails giftDetail) {
	        GiftDetails updatedgiftDetail = giftServiceInterface.updateGiftDetails(giftDetail);

        if (updatedgiftDetail != null) {
            GenericRes<GiftDetails> response = new GenericRes<>(200, "OK", "GIFT_DETAILS_UPDATED", updatedgiftDetail);
            return ResponseEntity.ok(response);
        } else {
            GenericRes<GiftDetails> response = new GenericRes<>(404, "NOT_FOUND", "GIFT_DETAILS_NOT_FOUND", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

 
 
 
 
 
 @DeleteMapping(URLConstants.DELETE_GIFT)
    public ResponseEntity<GenericRes> deleteGiftDetails(@RequestBody GiftDetails giftDetail) {
        boolean isDeleted = giftServiceInterface.deleteGiftDetails(giftDetail);

        if (isDeleted) {
            GenericRes response = new GenericRes<>(200, "OK", "GIFT_DETAILS_DELETED", "Deleted");
            return ResponseEntity.ok(response);
        } else {
            GenericRes response = new GenericRes<>(404, "NOT_FOUND", "GIFT_DETAILS_NOT_FOUND", "Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping(URLConstants.SAVE_GIFT_PURCHASE)
    public ResponseEntity<GenericRes<GiftPurchasedHistory>> saveGiftPurchasedHistory(@RequestBody GiftPurchasedHistoryRequest historyRequest) {
		GiftPurchasedHistory newDetails = giftServiceInterface.saveGiftPurchasedHistory(historyRequest);

        GenericRes<GiftPurchasedHistory> response = new GenericRes<>(200, "OK", "DIAMOND_DETAILS_CREATED", newDetails);
        return ResponseEntity.ok(response);
    }
 
 
 
 
 @GetMapping(URLConstants.GET_GIFT_PURCHASE)
    public ResponseEntity<GenericRes<GiftPurchasedHistory>> getGiftPurchasedHistoryById(@RequestBody GiftPurchasedHistory historyRequest) {
	 GiftPurchasedHistory newDetails = giftServiceInterface.getGiftPurchasedHistoryById(historyRequest);

        if (newDetails != null) {
            GenericRes<GiftPurchasedHistory> response = new GenericRes<>(200, "OK", "PURCHASED_HISTORY_FOUND", newDetails);
            return ResponseEntity.ok(response);
        } else {
            GenericRes<GiftPurchasedHistory> response = new GenericRes<>(404, "NOT_FOUND", "PURCHASED_HISTORY_NOT_FOUND", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
 
 
 
 @GetMapping(URLConstants.GET_ALL_GIFT_PURCHASE)
    public ResponseEntity<List<GenericRes<GiftPurchasedHistory>>> getAllGiftPurchasedHistory() {
	 List<GiftPurchasedHistory> historyList = giftServiceInterface.getAllGiftPurchasedHistory();

        List<GenericRes<GiftPurchasedHistory>> responseList = historyList.stream()
                .map(giftPurchasedHistory -> new GenericRes<>(200, "OK", "PURCHASED_HISTORY_FOUND", giftPurchasedHistory))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
 
 

 @PutMapping(URLConstants.UPDATE_GIFT_PURCHASE)
    public ResponseEntity<GenericRes<GiftPurchasedHistory>> updateGiftPurchasedHistory(
    		@RequestBody GiftPurchasedHistory historyRequest) {
	 GiftPurchasedHistory updatedgiftDetail = giftServiceInterface.updateGiftPurchasedHistory(historyRequest);

        if (updatedgiftDetail != null) {
            GenericRes<GiftPurchasedHistory> response = new GenericRes<>(200, "OK", "PURCHASED_HISTORY_UPDATED", updatedgiftDetail);
            return ResponseEntity.ok(response);
        } else {
            GenericRes<GiftPurchasedHistory> response = new GenericRes<>(404, "NOT_FOUND", "PURCHASED_HISTORY_NOT_FOUND", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

 
 
 
 
 
 @DeleteMapping(URLConstants.DELETE_GIFT_PURCHASE)
    public ResponseEntity<GenericRes> deleteGiftPurchasedHistory(@RequestBody GiftPurchasedHistory historyRequest) {
        boolean isDeleted = giftServiceInterface.deleteGiftPurchasedHistory(historyRequest);

        if (isDeleted) {
            GenericRes response = new GenericRes<>(200, "OK", "PURCHASED_HISTORY_DELETED", "Deleted");
            return ResponseEntity.ok(response);
        } else {
            GenericRes response = new GenericRes<>(404, "NOT_FOUND", "PURCHASED_HISTORY_NOT_FOUND", "Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}

























