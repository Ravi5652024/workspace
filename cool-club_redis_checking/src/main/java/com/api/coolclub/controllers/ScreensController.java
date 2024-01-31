package com.api.coolclub.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.models.request.GetSubCategoriesReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetScreenRes;
import com.api.coolclub.models.response.GetSubCategoriesRes;
import com.api.coolclub.services.CMSService;
import com.api.coolclub.services.ScreensService;
import com.api.coolclub.utils.HelperUtil;
import com.api.coolclub.utils.URIConstants;
import com.google.gson.Gson;


/*
 * @Author Rohan_Sharma
*/

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URIConstants.API_VERSION+URIConstants.SCREENS_BASE_URL)
public class ScreensController {
    private static final Logger log = LoggerFactory.getLogger(ScreensController.class);

    private final Gson gson;
    private final ScreensService screensService;
    private final HelperUtil helperUtil;
    private final CMSService cmsService;
    public ScreensController(Gson gson, ScreensService screensService, HelperUtil helperUtil, CMSService cmsService){
        this.gson = gson;
        this.screensService = screensService;
        this.helperUtil = helperUtil;
        this.cmsService = cmsService;
    }

    /* ---------------------------------- GET SCREEN DATA ----------------------------------- */
    @GetMapping(URIConstants.GET_SCREEN)
    public <T> ResponseEntity<GenericRes<T>> getScreenData(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @PathVariable("screenType") String screenType) {
        // String authUser = helperUtil.getUserNameFromJwt(auth);
        String authUser = "auth";
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        log.info("## [getScreenData]: request - lang: {}, operatorId: {}, screenType: {}", lang, operatorId, screenType);
        ResponseEntity<GenericRes<T>> response = null;
        if(screenType.equalsIgnoreCase("content")){
            response = cmsService.getScreenAssetsFromCMS(authUser, operatorId, lang);
        }else{
           response = screensService.getScreenData(authUser, lang, operatorId, screenType);
        }
       
        log.info("-- [getScreenData]-{}: response - {}",authUser,gson.toJson(response.getStatusCode()));
        return response;
    }

    /* ---------------------------------- GET SUB CATEGORY ----------------------------------- */
    @PostMapping(URIConstants.GET_SUB_CATEGORIES)
    public ResponseEntity<GenericRes<List<GetSubCategoriesRes>>> getSubCategories(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang,  @RequestHeader(name = "operatorId") Integer operatorId, @RequestBody GetSubCategoriesReq subCategdata) {
        // String authUser = helperUtil.getUserNameFromJwt(auth);
        String authUser = "auth";
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        log.info("## [getSubCategories]-{}: request - lang: {}, operatorId: {}, data: {}",authUser, lang, operatorId, gson.toJson(subCategdata));
        ResponseEntity<GenericRes<List<GetSubCategoriesRes>>> response = screensService.getSubCategories(authUser, lang, operatorId, subCategdata);
        log.info("-- [getSubCategories]-{}: response - {}",authUser,gson.toJson(response.getStatusCode()));
        return response;
    }
    
}
