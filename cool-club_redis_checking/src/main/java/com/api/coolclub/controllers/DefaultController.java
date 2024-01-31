package com.api.coolclub.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetContentRes;
import com.api.coolclub.models.response.GetScreenRes;
import com.api.coolclub.models.response.GetScreenVersionRes;
import com.api.coolclub.models.response.JwtTokenRes;
import com.api.coolclub.services.CMSService;
import com.api.coolclub.services.ScreensService;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.HelperUtil;
import com.api.coolclub.utils.URIConstants;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Parameter;
import springfox.documentation.annotations.ApiIgnore;

/*
 * @Author Rohan_Sharma
*/

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URIConstants.API_VERSION+URIConstants.DEFAULT_BASE_URL)
public class DefaultController {
    private static final Logger log = LoggerFactory.getLogger(DefaultController.class);

    private final Gson gson;
    private final HelperUtil helperUtil;
    private final CMSService cmsService;
    private final ScreensService screensService;
    public DefaultController(Gson gson, HelperUtil helperUtil, CMSService cmsService, ScreensService screensService){
        this.gson = gson;
        this.helperUtil = helperUtil;
        this.cmsService = cmsService;
        this.screensService = screensService;
    }

    /* ------------------------------------- MODULE CHECK ------------------------------------- */
    @GetMapping("/")
    @ApiIgnore
    public ResponseEntity<GenericRes<JwtTokenRes>> moduleCheck() {
        return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, "API UP AND RUNNING"), HttpStatus.OK);
    }

    // @RequestHeader(name = "timestamp", required = false) String timestamp
    /* ---------------------------------- GET SCREN VERSONING ---------------------------------- */
    @GetMapping(URIConstants.GET_SCREEN_VERSION)
    public ResponseEntity<GenericRes<List<GetScreenVersionRes>>> getScreenVersion(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        String authUser = "system";
        log.info("## [getScreenVersion]-{}: request - lang: {}, operatorId: {} ",authUser ,lang,operatorId);
        ResponseEntity<GenericRes<List<GetScreenVersionRes>>> response = screensService.getScreenVersion(authUser, lang, operatorId);
        log.info("-- [getScreenVersion]-{}: response - {}",authUser,gson.toJson(response.getStatusCode()));
        return response;
    }

    /* ------------------------------------- GET CONTENT ------------------------------------- */
    @GetMapping(URIConstants.GET_CONTENT)
    public ResponseEntity<GenericRes<GetContentRes>> getContent(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        String authUser = "system";
        log.info("## [getContent]-{}: request - lang: {}, operatorId: {} ",authUser ,lang,operatorId);
        ResponseEntity<GenericRes<GetContentRes>> response = cmsService.getScreenAssetsFromCMS(authUser, operatorId, lang);
        log.info("-- [getContent]-{}: response - {}",authUser,gson.toJson(response.getStatusCode()));
        return response;
    }

    /* ---------------------------------- GET PACK SCREEN ---------------------------------- */
    @GetMapping(URIConstants.GET_STARTUP_SCREEN)
    public ResponseEntity<GenericRes<List<GetScreenRes>>> getPackScreen(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @Parameter(example = "values: packs,signup,home") @PathVariable("screenType") String screenType) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        String authUser = "system";
        log.info("## [getPackScreen]-{}: request - lang: {}, operatorId: {},  screenType: {}",authUser ,lang,operatorId,screenType);

        //-- CHECK SCREEN VALUE
        if(!screenType.equalsIgnoreCase(Constants.PACKS_SCREEN_TYPE) && !screenType.equalsIgnoreCase(Constants.SIGNUP_SCREEN_TYPE) && !screenType.equalsIgnoreCase(Constants.HOME_SCREEN_TYPE)){
            log.info("-- [getPackScreen]-{}: response - INVALID SCREENTYPE",authUser);
            String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_PARAMETERS).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
        }
       
        ResponseEntity<GenericRes<List<GetScreenRes>>> response = screensService.getScreenData(authUser, lang, operatorId,screenType.toLowerCase());
        log.info("-- [getPackScreen]-{}: response - {}",authUser,gson.toJson(response.getStatusCode()));
        return response;
    }

}
