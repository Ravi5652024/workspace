package com.api.coolclub.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.entities.OtpHistory;
import com.api.coolclub.entities.UserProfile;
import com.api.coolclub.enums.GenderEnum;
import com.api.coolclub.enums.SubscriptionStatusEnum;
import com.api.coolclub.enums.UserProfileStatusEnum;
import com.api.coolclub.models.request.LogInReq;
import com.api.coolclub.models.request.RegisterReq;
import com.api.coolclub.models.request.UpdateUserProfileReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetUserProfileDetails;
import com.api.coolclub.models.response.JwtTokenRes;
import com.api.coolclub.repository.OtpHistoryRepo;
import com.api.coolclub.repository.UserProfileRepo;
import com.api.coolclub.security.JwtUtil;
import com.api.coolclub.services.UserProfileService;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.EncDecPasswordUtil;
import com.api.coolclub.utils.HelperUtil;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/


@Service
public class UserProfileServiceImpl implements UserProfileService{
    private static final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final Gson gson;
    private final UserProfileRepo userProfileRepo;
    private final OtpHistoryRepo otpHistoryRepo;
    private final HelperUtil helperUtil;
    public UserProfileServiceImpl(Gson gson, UserProfileRepo userProfileRepo, HelperUtil helperUtil, OtpHistoryRepo otpHistoryRepo){
        this.gson = gson;
        this.userProfileRepo = userProfileRepo;
        this.helperUtil = helperUtil;
        this.otpHistoryRepo = otpHistoryRepo;
    }

    /* --------------------------------------- SIGNUP --------------------------------------- */
    @Override
    public ResponseEntity<GenericRes<JwtTokenRes>> register(RegisterReq registerData, String lang) {
        try {
            if(registerData.getUserName() == null){
                log.warn("-- [register]-{}: invalid parameter",registerData.getUserName());
                String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_PARAMETERS).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
            }

            //-- checking is username is email/msisdn then check is user already exist
            Boolean isEmail = helperUtil.isEmail(registerData.getUserName());
            long count = Boolean.TRUE.equals(isEmail) ? userProfileRepo.countByEmail(registerData.getUserName()) : userProfileRepo.countByMsisdn(registerData.getUserName());
            log.debug("-- [register]-{}: findUser: {}",registerData.getUserName(),count);

            if(count != 0 ){
                log.warn("-- [register]-{}: userName already present",registerData.getUserName());
                String msg = GeneralConfig.globalizedMessages.get(Constants.USERNAME_AREADY_EXIST).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.CONFLICT_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.CONFLICT);
            }
            
            //-- OTP validation
            Boolean verifyOtp = isOTPVerified(registerData.getOtp(), registerData.getFlowType(), registerData.getUserName());
            if(Boolean.FALSE.equals(verifyOtp)){
                String msg = GeneralConfig.globalizedMessages.get(Constants.WRONG_OTP).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.CONFLICT_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.CONFLICT);
            }

            UserProfile user = new UserProfile(registerData.getDeviceType(), registerData.getDeviceId(), UserProfileStatusEnum.ACTIVE);
            if(Boolean.TRUE.equals(isEmail)){
                user.setEmail(registerData.getUserName());
            }else{
                user.setMsisdn(registerData.getUserName());
            }

            userProfileRepo.save(user);
            
            String token = helperUtil.generateToken(registerData.getUserName());

            String msg = GeneralConfig.globalizedMessages.get(Constants.SIGNUP_SUCCESSFULY).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, msg, new JwtTokenRes(token, user.getId())), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [register]-{} : ERROR", registerData.getUserName(),e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* --------------------------------------- LOGIN --------------------------------------- */
    @Override
    public ResponseEntity<GenericRes<JwtTokenRes>> logIn(LogInReq logInData, String lang) {
        try {
            if(logInData.getUserName() == null || logInData.getOtp() == null){
                String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_PARAMETERS).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.BAD_REQUEST);
            }

            //-- checking is username is email/msisdn then check is user exists
            Boolean isEmail = helperUtil.isEmail(logInData.getUserName());
            UserProfile userData = Boolean.TRUE.equals(isEmail) ? userProfileRepo.findByEmail(logInData.getUserName()) : userProfileRepo.findByMsisdn(logInData.getUserName());
            log.debug("-- [logIn]-{}: findUserDetails: {}",logInData.getUserName(),gson.toJson(userData));

            if(userData == null){
                log.warn("-- [logIn]-{}: username not present",logInData.getUserName());
                String msg = GeneralConfig.globalizedMessages.get(Constants.INVALID_CRED).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.UNAUTHORIZED_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.UNAUTHORIZED);
            }

            Boolean verifyOtp = isOTPVerified(logInData.getOtp(),null,logInData.getUserName());
            if(Boolean.FALSE.equals(verifyOtp)){
                String msg = GeneralConfig.globalizedMessages.get(Constants.WRONG_OTP).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.CONFLICT_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.CONFLICT);
            }

            String token = helperUtil.generateToken(logInData.getUserName());

            String msg = GeneralConfig.globalizedMessages.get(Constants.LOGIN_SUCCESSFULY).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, msg, new JwtTokenRes(token,userData.getId())), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [logIn]-{} : ERROR", logInData.getUserName(),e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* --------------------------------------- VERIFY OTP --------------------------------------- */
    private Boolean isOTPVerified(String otp, String flowType, String userName){
        try {
            if(otp.equals(GeneralConfig.globalVariablesMap.get(Constants.MASTER_OTP))){
                return true;
            }

            if(flowType == null || !flowType.equalsIgnoreCase("he")){
                Optional<OtpHistory> otpDetails = otpHistoryRepo.findOtpAndcreationTimeByUserName(userName);
                if(otpDetails.isPresent()){
                    OtpHistory otpHistory = otpDetails.get();
                    log.debug("-- [isOTPVerified]-{} : otpHistory: {}",userName,gson.toJson(otpHistory));
                    // long creationTime = otpHistory.getCreationTime();
                    // TODO: HANDLE OTP EXPIRY TIME
                    if(!otp.equals(otpHistory.getOtp())){
                        return false;
                    }

                    otpHistoryRepo.deleteByUserName(userName);
                }else{
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("-- [isOTPVerified]-{} : ERROR", userName,e);
            return false;
        }
    }

    /* ---------------------------------- GET PROFILE DETAILS ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<GetUserProfileDetails>> getProfileDetails(String userId, String lang) {
        try {
            // Boolean isEmail = helperUtil.isEmail(authUser);
            // UserProfile userData = Boolean.TRUE.equals(isEmail) ? userProfileRepo.findByEmail(authUser) : userProfileRepo.findByMsisdn(authUser);
            // log.debug("-- [getProfileDetails]-{}: findUserDetails: {}",authUser,gson.toJson(userData));

            UserProfile userData = null;
            Optional<UserProfile> user = userProfileRepo.findById(userId);
            if(user.isPresent()){
                userData = user.get();
            }
            if(userData == null){
                log.warn("-- [getProfileDetails]: username not present");
                return new ResponseEntity<>(new GenericRes<>(Constants.UNAUTHORIZED_CODE, Constants.UNSUCCESSFUL, Constants.INVALID_JWT_TOKEN), HttpStatus.UNAUTHORIZED);
            }

            GetUserProfileDetails response = new GetUserProfileDetails();
            BeanUtils.copyProperties(response, userData);
            response.setGender(helperUtil.getGenderString(userData.getGender(), lang));

            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null,response), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [getProfileDetails] : ERROR",e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }

       
    }

    /* -------------------------------- UPDATE PROFILE DETAILS --------------------------------- */
    @Override
    public ResponseEntity<GenericRes<String>> updateProfileDetails(String userId, UpdateUserProfileReq profileData, String lang) {
        try {
            UserProfile userData = null;
            Optional<UserProfile> user = userProfileRepo.findById(userId);
            if(user.isPresent()){
                userData = user.get();
            }

            if(userData == null){
                log.warn("-- [updateProfileDetails]: invalid userID: {}", userId);
                return new ResponseEntity<>(new GenericRes<>(Constants.BAD_REQUEST_CODE, Constants.UNSUCCESSFUL, Constants.INVALID_JWT_TOKEN), HttpStatus.BAD_REQUEST);
            }

            //-- checking unique nickName
            if(profileData.getNickName() != null){
                long count = userProfileRepo.countByNickName(profileData.getNickName());
                if(count > 0){
                    log.warn("-- [updateProfileDetails]: nickName already present");
                    String msg = GeneralConfig.globalizedMessages.get(Constants.NICKNAME_AREADY_EXIST).get(lang);
                    return new ResponseEntity<>(new GenericRes<>(Constants.CONFLICT_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.CONFLICT);
                }
            }

            //--- getting gender enum according to gender lang
            if(profileData.getGender() != null){
                GenderEnum genderEnum = helperUtil.getGenderEnumFromString(profileData.getGender().trim(),lang);
                userData.setGender(genderEnum);
            }

            Optional.ofNullable(profileData.getNickName()).ifPresent(userData::setNickName);
            Optional.ofNullable(profileData.getAge()).ifPresent(userData::setAge);

            userProfileRepo.save(userData);

            String msg = GeneralConfig.globalizedMessages.get(Constants.PROFILE_UPDATED).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, msg), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [updateProfileDetails] : ERROR",e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
