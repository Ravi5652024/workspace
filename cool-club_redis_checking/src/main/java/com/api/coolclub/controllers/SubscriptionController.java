package com.api.coolclub.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.enums.SubscriptionStatusEnum;
import com.api.coolclub.models.request.SubscriptionReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.SubCheckSubRes;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.HelperUtil;
import com.api.coolclub.utils.URIConstants;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URIConstants.API_VERSION+URIConstants.SUBSCRIPTION_BASE_URL)
public class SubscriptionController {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    private final Gson gson;
    private final HelperUtil helperUtil;
    public SubscriptionController(Gson gson, HelperUtil helperUtil){
        this.gson = gson;
        this.helperUtil = helperUtil;
    }

    /* ---------------------------------- SUBSCRIBE ---------------------------------- */
    @PostMapping(URIConstants.SUBSCRIBE)
    public ResponseEntity<GenericRes<SubCheckSubRes>> subscribe(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @RequestBody SubscriptionReq subReq) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        String authUser = "system";

        SubCheckSubRes res = new SubCheckSubRes(SubscriptionStatusEnum.ACTIVE);
        log.info("## [subscribe]-{}: request - lang: {}, operatorId: {} , data: {}",authUser ,lang,operatorId, gson.toJson(subReq));
        return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null,res), HttpStatus.OK);
    }

    /* ---------------------------------- CHECK-SUBSCRIBE ---------------------------------- */
    @GetMapping(URIConstants.CHECK_SUBSCRIPTION)
    public ResponseEntity<GenericRes<SubCheckSubRes>> checkSubscrption(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId,  @PathVariable("userId") String userId) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        String authUser = "system";

        SubCheckSubRes res = new SubCheckSubRes(SubscriptionStatusEnum.ACTIVE);
        log.info("## [checkSubscrption]-{}: request - lang: {}, operatorId: {} , userId:{}",authUser ,lang,operatorId,userId);
        return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null,res), HttpStatus.OK);
    }
}
