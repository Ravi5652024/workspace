package com.api.coolclub.servicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.coolclub.config.GeneralConfig;
import com.api.coolclub.entities.OperatorConfig;
import com.api.coolclub.models.pojo.GetSubCategoriesFromCMSpojo;
import com.api.coolclub.models.pojo.ResponsePojo;
import com.api.coolclub.models.request.GetSubCategoriesReq;
import com.api.coolclub.models.response.GenericRes;
import com.api.coolclub.models.response.GetContentRes;
import com.api.coolclub.models.response.GetSubCategoriesRes;
import com.api.coolclub.repository.OperatorConfigRepo;
import com.api.coolclub.services.CMSService;
import com.api.coolclub.utils.APICallHandlerUtil;
import com.api.coolclub.utils.CMSJsonParserUtil;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.EncDecPasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Service
public class CMSServiceImpl implements CMSService{
    private static final Logger log = LoggerFactory.getLogger(CMSServiceImpl.class);

    private final Gson gson;
    private final OperatorConfigRepo operatorConfigRepo;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CMSJsonParserUtil cmsJsonParserUtil;
    public CMSServiceImpl(Gson gson, OperatorConfigRepo operatorConfigRepo, RestTemplate restTemplate, ObjectMapper objectMapper, CMSJsonParserUtil cmsJsonParserUtil){
        this.gson = gson;
        this.operatorConfigRepo = operatorConfigRepo;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.cmsJsonParserUtil = cmsJsonParserUtil;
    }

    /* ---------------------------------- GENERATE CMS JWT TOKEN ----------------------------------- */
    private String cmsAuthenticate(String authUser, Integer operatorId){
        try {
            Optional<OperatorConfig> operatorConfigData = operatorConfigRepo.findByUuid(operatorId);
            Map<String, String> body = new HashMap<>();
            operatorConfigData.ifPresent(data -> {
                log.debug("--[cmsAuthenticate]-{} : operatorData:{}",authUser,gson.toJson(data));
                body.put("password", data.getCmsPassword());
                body.put("username", data.getCmsUsername());
            });

            String decPassword = EncDecPasswordUtil.decrypt(body.get("password"));
            body.put("password", decPassword);
            String requestBodyJson =  objectMapper.writeValueAsString(body);
            String url = GeneralConfig.globalVariablesMap.get(Constants.GENERATE_JWT_API);

            log.info("-- [cmsAuthenticate]-{}: generateJWT: apiRequest : url: {}, body: {}",authUser, url, gson.toJson(requestBodyJson));
            ResponsePojo apiResponse = APICallHandlerUtil.hitPostAPI(authUser, url, requestBodyJson, restTemplate, null);
            log.info("-- [cmsAuthenticate]-{}: generateJWT: apiResponse : {}",authUser, gson.toJson(apiResponse));
            
            HttpStatus statusCode = apiResponse.statusCode();
            JSONObject response = apiResponse.response();
            if(statusCode.is2xxSuccessful() && response != null && response.has("jwtToken")){
                String jwt = response.getString("jwtToken");
                GeneralConfig.operatorsDetails.get(operatorId).setJwt(jwt);
                return jwt;
            }

            return null;
        } catch (Exception e) {
            log.error("-- [cmsAuthenticate]-{} : ERROR",authUser,e);
            return null;
        }
    }

    /* ---------------------------------- GET TEMPLATE DATA FROM CMS ----------------------------------- */
    @Override
    public ResponseEntity<GenericRes<GetContentRes>> getScreenAssetsFromCMS(String authUser, Integer operatorId, String lang) {
        try {
            String jwt = GeneralConfig.operatorsDetails.get(operatorId).getJwt();
            if(jwt == null){
                jwt = cmsAuthenticate(authUser, operatorId);
                if(jwt == null){
                    log.error("-- [getScreenAssetsFromCMS]-{} : ERROR: UNABLE TO GET JWT TOKEN FROM CMS",authUser);
                    return null;
                }
            }

            GetContentRes response = getScreenAssetsFromCMSHelper(authUser,jwt,operatorId, lang, 1);
            return new ResponseEntity<>(new GenericRes<>(Constants.SUCCESS_CODE, Constants.SUCCESSFUL, null, response), HttpStatus.OK);
        } catch (Exception e) {
            log.error("-- [getScreenAssetsFromCMS]-{} : ERROR",authUser,e);
            return null;
        }
    }

    private GetContentRes getScreenAssetsFromCMSHelper(String authUser, String jwt, Integer operatorId, String lang, int retryLimit){
        if (retryLimit <= 0) {
            log.error("-- [getScreenAssetsFromCMSHelper]-{} : ERROR IN GETTING ASSETS FROM CMS",authUser);
            return null;
        }

        try {
            if(jwt == null){
                jwt = cmsAuthenticate(authUser, operatorId);
            }

            String url = GeneralConfig.globalVariablesMap.get(Constants.GET_SCREEN_DATA_API);
            log.info("-- [getScreenDataFromCMSHelper]-{}: apiRequest : url: {}, JWT: {}",authUser, url, jwt);
            ResponsePojo apiResponse = APICallHandlerUtil.hitGetAPI(authUser, url, restTemplate, jwt);
            log.info("-- [getScreenDataFromCMSHelper]-{}: apiResponse : {}",authUser, apiResponse.statusCode());
            log.debug("-- [getScreenDataFromCMSHelper]-{}: apiResponse : {}",authUser, gson.toJson(apiResponse));

            HttpStatus statusCode = apiResponse.statusCode();
            JSONObject response = apiResponse.response();
            if(statusCode.equals(HttpStatus.FORBIDDEN)){
                getScreenAssetsFromCMSHelper(authUser,null,operatorId,lang,retryLimit-1);
            }

            

            if(statusCode.is2xxSuccessful() && response != null && response.has("response")){
                JSONObject resObj = response.getJSONObject("response");
                if (!resObj.has(Constants.CMS_PLACEHOLDERS) || !resObj.has(Constants.CMS_CATEGORIES)) {
                    log.error("-- [getScreenAssetsFromCMSHelper]-{} : ERROR : placeholder and categories not in CMS response",authUser);
                    return null;
                }

                JSONArray placeholdersArray = resObj.getJSONArray(Constants.CMS_PLACEHOLDERS);
                Object placeholderContent = cmsJsonParserUtil.parsePlaceholder(authUser,placeholdersArray,lang);

                JSONArray categoriesArray = resObj.getJSONArray(Constants.CMS_CATEGORIES);
                Object categoriesContent = cmsJsonParserUtil.parseCategories(authUser,categoriesArray,lang);
                
                return new GetContentRes(placeholderContent,categoriesContent);
            }else{
                log.error("-- [getScreenAssetsFromCMSHelper]-{} : ERROR INVILID RESPONSE FROM CMS",authUser);
                return null;
            }
        } catch (Exception e) {
           log.error("-- [getTemplateDataFromCMSHelper]-{} : ERROR", authUser,e);
           return null;
        }
    }

    /* ---------------------------------- GET SUB CATEGORY ----------------------------------- */
    @Override
    public List<GetSubCategoriesRes> getSubCategoriesFromCMS(String authUser, String lang, Integer operatorId, GetSubCategoriesReq subCategdata) {
        try {
            String category = subCategdata.getCategory()+"."+subCategdata.getSubCategory();
            Integer pageSize = Integer.parseInt(GeneralConfig.globalVariablesMap.get(Constants.CMS_PAGE_SIZE));
            var body = new GetSubCategoriesFromCMSpojo(category,subCategdata.getType().toLowerCase(),lang,subCategdata.getPage(), pageSize);

            String requestBodyJson =  objectMapper.writeValueAsString(body);
            String url = GeneralConfig.globalVariablesMap.get(Constants.GET_SUB_CATEGORIES_API);

            return getSubCategoriesFromCMSHelper(authUser,url,requestBodyJson,operatorId,2);
            
        } catch (Exception e) {
            log.error("-- [getSubCategoriesFromCMS]-{} : ERROR",authUser,e);
            return new ArrayList<>();
        }
    }

    private List<GetSubCategoriesRes> getSubCategoriesFromCMSHelper(String authUser, String url, String requestBodyJson, Integer operatorId, int retryLimit) {

        if (retryLimit <= 0) {
            log.error("-- [getSubCategoriesFromCMSHelper]-{} : ERROR",authUser);
            return new ArrayList<>();
        }

        try {
            String token = GeneralConfig.operatorsDetails.get(operatorId).getJwt();
            if(token == null){
                token = cmsAuthenticate(authUser, operatorId);
                if(token == null){
                    log.error("-- [getSubCategoriesFromCMSHelper]-{} : ERROR: UNABLE TO REFRESH CMS TOKEN",authUser);
                    return new ArrayList<>();
                }
            }

            log.info("-- [getSubCategoriesFromCMSHelper]-{}: getSubCategories: apiRequest : url: {}, body: {}, token: {}",authUser, url, gson.toJson(requestBodyJson),token);
            ResponsePojo apiResponse = APICallHandlerUtil.hitPostAPI(authUser, url, requestBodyJson, restTemplate, token);
            log.info("-- [getSubCategoriesFromCMSHelper]-{}: getSubCategories: apiResponse : {}",authUser, apiResponse.statusCode());
            log.debug("-- [getSubCategoriesFromCMSHelper]-{}: getSubCategories: apiResponse : {}",authUser, gson.toJson(apiResponse));
            
            HttpStatus statusCode = apiResponse.statusCode();
            JSONObject response = apiResponse.response();
            if(statusCode.equals(HttpStatus.FORBIDDEN)){
                getSubCategoriesFromCMSHelper(authUser,url,requestBodyJson,operatorId,retryLimit-1); // recursive call function
            }
            
            if(statusCode.is2xxSuccessful() && response != null && response.has("image")){
                JSONArray subCategoriesArray = response.getJSONArray("image");
                return cmsJsonParserUtil.parseSubCategories(subCategoriesArray);
                
            }else{
                log.error("-- [getScreenAssetsFromCMSHelper]-{} : ERROR INVILID RESPONSE FROM CMS",authUser);
                return new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("-- [getSubCategoriesFromCMSHelper]-{} : ERROR",authUser,e);
            return new ArrayList<>();
        }
    }
    
}
