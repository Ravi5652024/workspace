package com.api.coolclub.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.api.coolclub.config.GeneralConfig;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Component
public class LanguageMappingUtil {
    private static final Logger log = LoggerFactory.getLogger(LanguageMappingUtil.class);

    private final Gson gson;
    public LanguageMappingUtil(Gson gson){
        this.gson = gson;
    }
    
    /* --------------------------------------- LANGUAGE MAPPING ON SCREEN --------------------------------------- */
    public Object languageMapping(String authUser, String jsonData, String language, Integer operatorId, String screenType) {
        JSONObject jsonDataObj = new JSONObject(jsonData);
        JSONObject contentObj = jsonDataObj.getJSONObject(Constants.SCREEN_CONTENT);
        JSONObject langObj = jsonDataObj.getJSONObject(Constants.SCREEN_LANGUAGE);
        log.debug("-- [languageMapping]-{} : contentObj: {}",authUser,gson.toJson(contentObj));
        log.debug("-- [languageMapping]-{} : screenLangObj: {}",authUser,gson.toJson(langObj));

        translate(contentObj, langObj, language);

        if(screenType.equalsIgnoreCase(Constants.HOME_SCREEN_TYPE)){
            List<String> languageSupport = GeneralConfig.operatorsDetails.get(operatorId).getLanguageSupport();
            JSONObject drawerContent = contentObj.getJSONObject("drawerContent");
            drawerContent.put("languages", languageSupport);
        }
        
        if(screenType.equalsIgnoreCase(Constants.PACKS_SCREEN_TYPE)){
            //TODO: GET PACKS DATA FROM SUBSCRIPTION
            JSONArray subscriptionlist = contentObj.getJSONArray("subscriptionlist");
            for(int i=0;i<subscriptionlist.length();i++){
                JSONObject obj = (JSONObject) subscriptionlist.get(i);
                if(i==0){
                    obj.put("duration", "month");
                    obj.put("diamonds", "30");
                    obj.put("amount", "10");
                    obj.put("currency", "$");
                }else{
                    obj.put("duration", "month");
                    obj.put("diamonds", "30");
                    obj.put("amount", "30");
                    obj.put("currency", "$");
                }
                
            }
        }

        return contentObj.toMap();
    }

    private static void translate(JSONObject contentObj, JSONObject langObj, String language) {
        for (String key : contentObj.keySet()) {
            Object value = contentObj.get(key);
            if (value instanceof JSONObject) {
                translate((JSONObject) value, langObj, language);
            } else if (value instanceof JSONArray) {
                processArray((JSONArray) value, langObj, language);
            } else if (value instanceof String) {
                // Check if the value is a key in the langObj JSON and maps to an array
                String translationKey = (String) value;
                if (langObj.has(translationKey) && langObj.getJSONObject(translationKey).has(language)) {
                    Object translationValue = langObj.getJSONObject(translationKey).get(language);
                    contentObj.put(key, translationValue);
                }
            }
        }
    }
    
    private static void processArray(JSONArray array, JSONObject langObj, String language) {
        for (int i = 0; i < array.length(); i++) {
            Object arrayItem = array.get(i);
            if (arrayItem instanceof JSONObject) {
                translate((JSONObject) arrayItem, langObj, language);
            } else if (arrayItem instanceof JSONArray) {
                processArray((JSONArray) arrayItem, langObj, language);
            } else if (arrayItem instanceof String) {
                String translatedValue = getTranslation((String) arrayItem, langObj, language);
                if (translatedValue != null) {
                    array.put(i, translatedValue);
                }
            }
        }
    }

    private static String getTranslation(String key, JSONObject langObj, String language) {
        if (langObj.has(key) && langObj.getJSONObject(key).has(language)) {
            return langObj.getJSONObject(key).getString(language);
        }
        return null;
    }

}
