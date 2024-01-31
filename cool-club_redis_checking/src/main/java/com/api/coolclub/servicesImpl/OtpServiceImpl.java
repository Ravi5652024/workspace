package com.api.coolclub.servicesImpl;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.entities.OtpHistory;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.repository.OtpHistoryRepo;
import com.api.coolclub.services.OtpService;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.HelperUtil;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Service
public class OtpServiceImpl implements OtpService{
    private static final Logger log = LoggerFactory.getLogger(OtpServiceImpl.class);

    private final Gson gson;
    private final HelperUtil helperUtil;
    private final OtpHistoryRepo otpHistoryRepo;
    public OtpServiceImpl(Gson gson, HelperUtil helperUtil, OtpHistoryRepo otpHistoryRepo){
        this.gson = gson;
        this.helperUtil = helperUtil;
        this.otpHistoryRepo = otpHistoryRepo;
    }

    @Override
    public ResponseEntity<GenericRes<String>> sendOTP(String userName, String lang, Integer operatorId) {
        try {
            Boolean isEmail = helperUtil.isEmail(userName);
            if(Boolean.TRUE.equals(isEmail)){
                // TODO: handle email send
            }else{
                // TODO: handle otp send
            }

            String otp = helperUtil.generateOTP(5);
            long currentTime = Instant.now().toEpochMilli();

            //-- if otp send succesfully
            OtpHistory lastDelivered = otpHistoryRepo.findByUserName(userName);
            log.debug("## [sendOTP]-{}: lastDeliveredOtpDetails: {}",userName,gson.toJson(lastDelivered));

            if(lastDelivered != null){
                // TODO: any other case handle
                lastDelivered.setOtp(otp);
                lastDelivered.setCreationTime(currentTime);

                otpHistoryRepo.save(lastDelivered);
            }else{
                otpHistoryRepo.save(new OtpHistory(userName, otp, currentTime));
            }

            String msg = GeneralConfig.globalizedMessages.get(Constants.OTP_SEND_SUCCESSFULY).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, msg), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [getScreenData]-{} : ERROR", userName,e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
