package com.api.coolclub.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.api.coolclub.models.request.GetSubCategoriesReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetScreenVersionRes;
import com.api.coolclub.models.response.GetSubCategoriesRes;

/*
 * @Author Rohan_Sharma
*/

public interface ScreensService {
    
    /*
	 * GET SCREEN VERSION
     * @params (authuser, lang and operatorId)
	*/
    public ResponseEntity<GenericRes<List<GetScreenVersionRes>>> getScreenVersion(String authUser, String lang, Integer operatorId);

    /*
	 * GET SCREEN DATA
     * @params (authuser and lang)
	*/
    public <T>ResponseEntity<GenericRes<T>> getScreenData(String authUser, String lang, Integer operatorId, String screenType);

    /*
	 * GET SUB CATEGORY
     * @params (authuser and lang)
	*/
    public ResponseEntity<GenericRes<List<GetSubCategoriesRes>>> getSubCategories(String authUser, String lang, Integer operatorId, GetSubCategoriesReq subCategdata);
}
