package com.api.coolclub.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.models.request.AddOperator;
import com.api.coolclub.models.request.UpdateOperatorReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetAllOperatorsRes;
import com.api.coolclub.services.OperatorService;
import com.api.coolclub.utils.HelperUtil;
import com.api.coolclub.utils.URIConstants;
/*
 * @Author Rohan_Sharma
*/
import com.google.gson.Gson;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URIConstants.API_VERSION+URIConstants.OPERATOR_BASE_URL)
public class OperatorController {
    private static final Logger log = LoggerFactory.getLogger(OperatorController.class);

    private final Gson gson;
    private final OperatorService operatorService;
    private final HelperUtil helperUtil;
    public OperatorController(Gson gson, OperatorService operatorService, HelperUtil helperUtil){
        this.gson = gson;
        this.operatorService = operatorService;
        this.helperUtil = helperUtil;
    }

    /* ---------------------------------- ADD OPERATOR ----------------------------------- */
    @PostMapping(URIConstants.ADD_OPERATOR)
    public ResponseEntity<GenericRes<String>> addOperator(@RequestHeader(name = "Authorization", required = false) String auth, @RequestBody AddOperator operatorData) {
        log.info("## [addOperator]: request: {}", gson.toJson(operatorData));
        ResponseEntity<GenericRes<String>> response = operatorService.addOperator(operatorData);
        log.info("-- [addOperator]: response - {}",gson.toJson(response.getBody()));
        return response;
    }

    /* ---------------------------------- GET ALL OPERATOR ----------------------------------- */
    @GetMapping(URIConstants.GET_OPERATOR)
    public ResponseEntity<GenericRes<List<GetAllOperatorsRes>>> getAllOperator(@RequestHeader(name = "Authorization", required = false) String auth) {
        // String authUser = helperUtil.getUserNameFromJwt(auth);
        log.info("## [getAllOperator]: request");
        ResponseEntity<GenericRes<List<GetAllOperatorsRes>>> response = operatorService.getAllOperator();
        log.info("-- [getAllOperator]: response - {}",gson.toJson(response.getStatusCode()));
        return response;
    }

    /* ---------------------------------- UPDATE OPERATOR ----------------------------------- */
    @PatchMapping(URIConstants.UPDATE_OPERATOR)
    public ResponseEntity<GenericRes<String>> updateOperator(@RequestHeader(name = "Authorization", required = false) String auth, @RequestBody UpdateOperatorReq operatorData) {
        log.info("## [updateOperator]: request - {}", gson.toJson(operatorData));
        ResponseEntity<GenericRes<String>> response = operatorService.updateOperator(operatorData);
        log.info("-- [updateOperator]: response - {}",gson.toJson(response.getBody()));
        return response;
    }
}
