package com.api.coolclub.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.entities.ScreenConfig;
import com.api.coolclub.models.request.GetSubCategoriesReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetScreenRes;
import com.api.coolclub.models.response.GetScreenVersionRes;
import com.api.coolclub.models.response.GetSubCategoriesRes;
import com.api.coolclub.repository.ScreenConfigRepo;
import com.api.coolclub.services.CMSService;
import com.api.coolclub.services.ScreensService;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.LanguageMappingUtil;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Service
public class ScreensServiceImpl implements ScreensService{
    private static final Logger log = LoggerFactory.getLogger(ScreensServiceImpl.class);

    private final Gson gson;
    private final CMSService cmsService;
    private final ScreenConfigRepo screenConfigRepo;
    private final LanguageMappingUtil languageMappingUtil;
    public ScreensServiceImpl(Gson gson,CMSService cmsService, ScreenConfigRepo screenConfigRepo, LanguageMappingUtil languageMappingUtil){
        this.gson = gson;
        this.cmsService = cmsService;
        this.screenConfigRepo = screenConfigRepo;
        this.languageMappingUtil = languageMappingUtil;
    }

    /* ---------------------------------- GET SCREN VERSONING ---------------------------------- */
    @Override
    public ResponseEntity<GenericRes<List<GetScreenVersionRes>>> getScreenVersion(String authUser, String lang, Integer operatorId) {
        try {
            List<ScreenConfig> data = screenConfigRepo.getAllKeyAndCurrentVersion();
            List<GetScreenVersionRes> response = new ArrayList<>();

            for(ScreenConfig screen : data){
                var res = new GetScreenVersionRes(screen.getKey(), screen.getCurrentVersion());
                response.add(res);
            }
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null,response),HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [getScreenData]-{} : ERROR", authUser,e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ---------------------------------- GET SCREEN DATA ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<GetScreenRes>> getScreenData(String authUser, String lang, Integer operatorId, String screenType) {
        try {
            //-- getting screen json
            screenType = screenType.trim();
            ScreenConfig screenData = screenConfigRepo.findByKetAndOperatorUuId(screenType,operatorId);
            log.debug("## [getScreenData]-{}: screenData: {}",authUser, gson.toJson(screenData));

            if(screenData == null){
                log.warn("## [getScreenData]-{}: invalid screenType: {}",authUser, screenType);
                String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
                return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Object json = languageMappingUtil.languageMapping(authUser, screenData.getValue(), lang, operatorId, screenType);

            GetScreenRes res = new GetScreenRes(json);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null,res), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [getScreenData]-{} : ERROR", authUser,e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ---------------------------------- GET SUB CATEGORY ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<List<GetSubCategoriesRes>>> getSubCategories(String authUser, String lang, Integer operatorId, GetSubCategoriesReq subCategdata) {
        try {
            List<GetSubCategoriesRes> res = cmsService.getSubCategoriesFromCMS(authUser, lang, operatorId, subCategdata);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null,res), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [getSubCategories]-{} : ERROR", authUser,e);
            String msg = GeneralConfig.globalizedMessages.get(Constants.SERVER_ERROR).get(lang);
            return new ResponseEntity<>(new GenericRes<>(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.UNSUCCESSFUL, msg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
