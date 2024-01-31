package com.api.coolclub.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.models.request.LogInReq;
import com.api.coolclub.models.request.RegisterReq;
import com.api.coolclub.models.request.UpdateUserProfileReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetUserProfileDetails;
import com.api.coolclub.models.response.JwtTokenRes;
import com.api.coolclub.services.UserProfileService;
import com.api.coolclub.utils.HelperUtil;
import com.api.coolclub.utils.URIConstants;
import com.google.gson.Gson;



/*
 * @Author Rohan_Sharma
*/

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URIConstants.API_VERSION+URIConstants.USER_BASE_URL)
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final Gson gson;
    private final UserProfileService userProfileService;
    private final HelperUtil helperUtil;
    public UserController(Gson gson, UserProfileService userProfileService, HelperUtil helperUtil){
        this.gson = gson;
        this.userProfileService = userProfileService;
        this.helperUtil = helperUtil;
    }

    /* --------------------------------------- REGISTER --------------------------------------- */
    @PostMapping(URIConstants.USER_REGISTER)
    public ResponseEntity<GenericRes<JwtTokenRes>> register(@RequestHeader(name = "Authorization", required = false) String auth, @RequestBody RegisterReq registerData, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        log.info("## [register]-{}: request - lang: {}, operatorId: {}, data:{}",registerData.getUserName(),lang,operatorId, gson.toJson(registerData));
        ResponseEntity<GenericRes<JwtTokenRes>> response = userProfileService.register(registerData,lang);
        log.info("-- [register]-{}: response - {}",registerData.getUserName(),gson.toJson(response.getBody()));
        return response;
    }
    
    /* --------------------------------------- LOGIN --------------------------------------- */
    @PostMapping(URIConstants.USER_LOGIN)
    public ResponseEntity<GenericRes<JwtTokenRes>> logIn(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @RequestBody LogInReq logInData) {
        lang = helperUtil.checkLanguageSupport(lang,operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        log.info("## [logIn]-{}: request - lang: {}, operatorId: {}, data:{}",logInData.getUserName(),lang,operatorId,gson.toJson(logInData));
        ResponseEntity<GenericRes<JwtTokenRes>> response = userProfileService.logIn(logInData,lang);
        log.info("-- [logIn]-{}: response - {}",logInData.getUserName(),gson.toJson(response.getBody()));
        return response;
    }
    
    /* ---------------------------------- GET PROFILE DETAILS ----------------------------------- */
    @GetMapping(URIConstants.USER_PROFILE)
    public ResponseEntity<GenericRes<GetUserProfileDetails>> getProfileDetails(@RequestHeader(name = "Authorization", required = false) String auth, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @PathVariable String userId) {
        // String authUser = helperUtil.getUserNameFromJwt(auth);
        lang = helperUtil.checkLanguageSupport(lang, operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        log.info("## [getProfileDetails]: request - lang: {}, operatorId: {}", lang, operatorId);
        ResponseEntity<GenericRes<GetUserProfileDetails>> response = userProfileService.getProfileDetails(userId,lang);
        log.info("-- [getProfileDetails]: response - {}",gson.toJson(response.getStatusCode()));
        return response;
    }

    /* -------------------------------- UPDATE PROFILE DETAILS --------------------------------- */
    @PatchMapping(URIConstants.USER_PROFILE)
    public ResponseEntity<GenericRes<String>> updateProfileDetails(@RequestHeader(name = "Authorization", required = false) String auth, @RequestBody UpdateUserProfileReq profileData, @RequestHeader(name = "language") String lang, @RequestHeader(name = "operatorId") Integer operatorId, @PathVariable String userId) {
        // String authUser = helperUtil.getUserNameFromJwt(auth);
        lang = helperUtil.checkLanguageSupport(lang, operatorId);
        if(lang == null){
            return helperUtil.getInvalidOperatorError();
        }
        log.info("## [updateProfileDetails]: request - lang: {}, operatorId: {}, data: {}", lang, operatorId, gson.toJson(profileData));
        ResponseEntity<GenericRes<String>> response = userProfileService.updateProfileDetails(userId, profileData, lang);
        log.info("-- [updateProfileDetails]: response - {}",gson.toJson(response.getBody()));
        return response;
    }
}
