package com.api.coolclub.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.api.coolclub.entities.GlobalConfig;
import com.api.coolclub.entities.OperatorConfig;
import com.api.coolclub.models.pojo.OperatorsDetailsPojo;
import com.api.coolclub.repository.GlobalConfigRepo;
import com.api.coolclub.repository.OperatorConfigRepo;
import com.api.coolclub.repository.ScreenConfigRepo;
import com.api.coolclub.services.CMSService;
import com.api.coolclub.utils.Constants;
import com.api.coolclub.utils.DbSetUpUtil;
import com.api.coolclub.utils.LanguageMappingUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class GeneralConfig {
    private static final Logger log = LoggerFactory.getLogger(GeneralConfig.class);

    public final Gson gson;
    public final GlobalConfigRepo globalConfigRepo;
    private DbSetUpUtil dbSetUpUtil;
    private OperatorConfigRepo operatorConfigRepo;
    public GeneralConfig(Gson gson, GlobalConfigRepo globalConfigRepo, DbSetUpUtil dbSetUpUtil, OperatorConfigRepo operatorConfigRepo){
        this.gson = gson;
        this.globalConfigRepo = globalConfigRepo;
        this.dbSetUpUtil = dbSetUpUtil;
        this.operatorConfigRepo = operatorConfigRepo;
    }

    //--- GLOBAL VARIABLES
    public static Map<String, String> globalVariablesMap = null; //-- store global variable
    public static Map<String, List<String>> gendersMap = null; //-- store global variable
    public static Map<Integer, OperatorsDetailsPojo> operatorsDetails = null; //-- store operaters details
    public static Map<String, Map<String, String>> globalizedMessages = null; //-- store response msg in different language

    @PostConstruct
	public void init() {
        dbSetUpUtil.initializeCollection();
        setUpGlobalVariable();
        setUpOperators();
        serverStarted();
    }

    /* --------------------------------------- SETUP : GLOBAL VARIABLE --------------------------------------- */
    public void setUpGlobalVariable(){
        try {
            globalVariablesMap = new HashMap<>();
            List<GlobalConfig> data = globalConfigRepo.findAllKeyValues();
            for (GlobalConfig entry : data) {
                if(entry.getKey().equals(Constants.GLOBALIZED_MESSAGES)){
                    setUpglobalizedMessages(entry.getValue());
                }else if(entry.getKey().equals(Constants.GENDERS)){
                    setUpGenderData(entry.getValue());
                }else{
                    globalVariablesMap.put(entry.getKey(), entry.getValue());
                }
            }
            log.debug("[globalVariablesMap]: {}",gson.toJson(globalVariablesMap));
            log.debug("[globalizedMessages]: {}",gson.toJson(globalizedMessages));
            log.debug("[gendersMap]: {}",gendersMap);
            log.info("SETUP DONE: GLOBAL VARIABLE");
        } catch (Exception e) {
            log.error("-- ERROR : SETUP: GLOBAL VARIABLE",e);
        }
    }

    /* --------------------------------------- HELPER : TO MAKE LANGUAGE HASHMAP --------------------------------------- */
    private static void setUpglobalizedMessages(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode languageNode = objectMapper.readTree(jsonString);
            globalizedMessages = new HashMap<>();

            languageNode.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode valueNode = entry.getValue();

                Map<String, String> valueMap = new HashMap<>();
                valueNode.fields().forEachRemaining(languageEntry -> {
                    String languageKey = languageEntry.getKey();
                    String languageValue = languageEntry.getValue().asText();
                    valueMap.put(languageKey, languageValue);
                });

                globalizedMessages.put(key, valueMap);
            });
        } catch (IOException e) {
            log.error("-- ERROR : SETUP: GLOBAL LANGUAGE HASHMAP",e);
        }
    }
    
    /* --------------------------------------- HELPER : TO MAKE GENDERS ARRAYS --------------------------------------- */
    private static void setUpGenderData(String jsonString){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> genderData = objectMapper.readValue(jsonString, new TypeReference<Map<String, String>>() {});
            gendersMap = new HashMap<>();
            for (Map.Entry<String, String> entry : genderData.entrySet()) {
                String language = entry.getKey();
                String valuesString = entry.getValue();
                List<String> valuesList = List.of(valuesString.split(","));
                gendersMap.put(language, valuesList);
            }
        } catch (Exception e) {
            log.error("-- ERROR : SETUP: GENDERS HASHMAP",e);
        }
        
    }

    /* --------------------------------------- SETUP : OPERATORS DETAILS --------------------------------------- */
    public void setUpOperators(){
        try {
            operatorsDetails = new HashMap<>();
            List<OperatorConfig> operators = operatorConfigRepo.findAllNameLang();
            for(OperatorConfig operator: operators){
                operatorsDetails.put(operator.getUuid(), new OperatorsDetailsPojo(operator.getDefaultLanguage(), operator.getLanguageSupport(),null));
            }
            log.debug("[operatorsDetails]: {}",gson.toJson(operatorsDetails));
            log.info("SETUP DONE: OPERATORS DETAILS");
        } catch (Exception e) {
            log.error("-- ERROR : SETUP: OPERATORS DETAILS",e);
        }
    }
    
    /* --------------------------------------- SERVER STARTED LOG --------------------------------------- */
    public void serverStarted(){
        log.info("-----------------------------------------------");
        log.info("|         SERVER SUCCESSFULLY STARTED         |");
        log.info("-----------------------------------------------");
    }
}
