package com.api.coolclub.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.api.coolclub.models.request.GetSubCategoriesReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetSubCategoriesRes;

/*
 * @Author Rohan_Sharma
*/

public interface CMSService {

    /*
	 * GET SCREEN ASSETS FROM CMS
     * @params ()
	*/
    public <T> ResponseEntity<GenericRes<T>> getScreenAssetsFromCMS(String authUser, Integer operatorId, String lang);

    /*
	 * GET SUN CONFIG FROM CMS
     * @params ()
	*/
    public List<GetSubCategoriesRes> getSubCategoriesFromCMS(String authUser, String lang, Integer operatorId, GetSubCategoriesReq subCategdata);
}
