package com.api.coolclub.services;

import org.springframework.http.ResponseEntity;

import com.api.coolclub.models.request.LogInReq;
import com.api.coolclub.models.request.RegisterReq;
import com.api.coolclub.models.request.UpdateUserProfileReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetUserProfileDetails;
import com.api.coolclub.models.response.JwtTokenRes;

/*
 * @Author Rohan_Sharma
*/

public interface UserProfileService {
    
    /*
	 * USER REGISTER
     * @params (Refister data)
	*/
    public ResponseEntity<GenericRes<JwtTokenRes>> register(RegisterReq registerData, String lang);

    /*
	 * USER LOGIN
     * @params (login data)
	*/
    public ResponseEntity<GenericRes<JwtTokenRes>> logIn(LogInReq logInData, String lang);

    /*
	 * GET USER PROFILE DETAILS
     * @params (Register data)
	*/
    public ResponseEntity<GenericRes<GetUserProfileDetails>> getProfileDetails(String userId, String lang);

    /*
	 * UPDATE USER PROFILE DETAILS
     * @params (data)
	*/
    public ResponseEntity<GenericRes<String>> updateProfileDetails(String userId, UpdateUserProfileReq profileData, String lang);
}
