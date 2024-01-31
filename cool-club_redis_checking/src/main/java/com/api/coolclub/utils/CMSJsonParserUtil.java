package com.api.coolclub.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.api.coolclub.models.pojo.CategoriesHelperPojo;
import com.api.coolclub.models.pojo.CategoriesPojo;
import com.api.coolclub.models.pojo.ImageResolutionPojo;
import com.api.coolclub.models.response.GetSubCategoriesRes;
import com.api.coolclub.redis.RedisService;
import com.google.gson.Gson;

/*
 * @Author Rohan_Sharma
*/

@Component
public class CMSJsonParserUtil {
    private static final Logger log = LoggerFactory.getLogger(CMSJsonParserUtil.class);

    private final Gson gson;
    public CMSJsonParserUtil(Gson gson){
        this.gson = gson;
    }

    /* --------------------------------------- PARSE PLACEHOLDER JSON --------------------------------------- */
    public Object parsePlaceholder(String authUser, JSONArray placeholdersArray, String lang){
        try {
            Map<String, List<ImageResolutionPojo>> result = new HashMap<>();
            
            for (int i = 0; i < placeholdersArray.length(); i++) {
                JSONObject placeholderObj = placeholdersArray.getJSONObject(i);
                String placeholder = placeholderObj.getString(Constants.CMS_PLACEHOLDER);

                Object languageField = placeholderObj.get(Constants.CMS_LANGUAGE);
                if(languageField instanceof JSONObject){
                    JSONObject languageObj = placeholderObj.getJSONObject(Constants.CMS_LANGUAGE);
                    String shortcode = languageObj.getString(Constants.CMS_SHORTCODE);

                    if(shortcode.equalsIgnoreCase(lang)){
                        JSONArray metadataIdsArray = placeholderObj.getJSONArray(Constants.CMS_METADATAIDS);
                        List<ImageResolutionPojo> resolutionsList = new ArrayList<>();
        
                        for (int j = 0; j < metadataIdsArray.length(); j++) {
                            JSONObject metadata = metadataIdsArray.getJSONObject(j);
                            JSONObject resolutions = metadata.getJSONObject(Constants.CMS_CONTENT).getJSONObject(Constants.CMS_RESOLUTIONS);
        
                            String low = resolutions.getJSONObject(Constants.CMS_LOW_RESOLUTION).getString(Constants.CMS_URL);
                            String mid = resolutions.getJSONObject(Constants.CMS_MID_RESOLUTION).getString(Constants.CMS_URL);
                            String high = resolutions.getJSONObject(Constants.CMS_HIGH_RESOLUTION).getString(Constants.CMS_URL);
        
                            var imageData = new ImageResolutionPojo(low,mid,high);
        
                            resolutionsList.add(imageData);
                        }
                        result.computeIfAbsent(placeholder, k -> new ArrayList<>()).addAll(resolutionsList);
                    }
                }
                
            }

            return result;
        } catch (Exception e) {
            log.error("-- [parsePlaceholder]-{} : ERROR ",authUser, e);
            return false;
        }
    }

    /* --------------------------------------- PARSE CATEGORIES JSON --------------------------------------- */
    public Object parseCategories(String authUser, JSONArray categoriesArray, String lang) {
        try {
            return IntStream.range(0, categoriesArray.length())
                    .mapToObj(categoriesArray::getJSONObject)
                    .filter(categoryObj -> categoryObj.getString(Constants.CMS_LANGUAGE).equalsIgnoreCase(lang))
                    .flatMap(categoryObj -> parseCategoriesHelper(authUser, categoryObj.getJSONArray(Constants.CMS_VALUE)).stream())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("-- [parseCategories]-{} : ERROR ",authUser, e);
            return null;
        }
    }

    private List<CategoriesPojo> parseCategoriesHelper(String authUser, JSONArray valuesArray){
        try {
            List<CategoriesPojo> result = new ArrayList<>();

            for (int j = 0; j < valuesArray.length(); j++) {
                JSONObject valueObj = valuesArray.getJSONObject(j);
                CategoriesPojo categoriesPojo = new CategoriesPojo();
                categoriesPojo.setTitle(valueObj.getString(Constants.CMS_NAME));

                JSONObject thumbMetaData1 = valueObj.getJSONObject(Constants.CMS_THUMBIMAGEMETADATA);
                String type = thumbMetaData1.getString(Constants.CMS_CONTENT_TYPE);
                categoriesPojo.setType(type);

                JSONArray nextSectionArray = valueObj.optJSONArray(Constants.CMS_NEXTSECTION);
                List<CategoriesHelperPojo> helperList = new ArrayList<>();

                if (nextSectionArray != null) {
                    for (int k = 0; k < nextSectionArray.length(); k++) {
                        JSONObject nextSectionObj = nextSectionArray.getJSONObject(k);
                        CategoriesHelperPojo helperPojo = new CategoriesHelperPojo();
                        helperPojo.setName(nextSectionObj.getString(Constants.CMS_NAME));
                        helperPojo.setPosition(nextSectionObj.getInt(Constants.CMS_POSITION));

                        JSONObject thumbMetaData = nextSectionObj.getJSONObject(Constants.CMS_THUMBIMAGEMETADATA);
                        JSONObject contentObj = thumbMetaData.getJSONObject(Constants.CMS_CONTENT);
                        JSONObject resolutionsObj = contentObj.getJSONObject(Constants.CMS_RESOLUTIONS);

                        String low = resolutionsObj.getJSONObject(Constants.CMS_LOW_RESOLUTION).getString(Constants.CMS_URL);
                        String mid = resolutionsObj.getJSONObject(Constants.CMS_MID_RESOLUTION).getString(Constants.CMS_URL);
                        String high = resolutionsObj.getJSONObject(Constants.CMS_HIGH_RESOLUTION).getString(Constants.CMS_URL);

                        helperPojo.setThumbUrl(new ImageResolutionPojo(low, mid, high));
                        helperPojo.setSubtitle("na");
                        helperList.add(helperPojo);
                    }
                }
                
                //-- sorting
                Collections.sort(helperList, Comparator.comparing(CategoriesHelperPojo::getPosition));

                categoriesPojo.setCategories(helperList);
                result.add(categoriesPojo);
            }

            return result;
        } catch (Exception e) {
            log.error("-- [parseCategoriesHelper]-{} : ERROR ",authUser, e);
            return new ArrayList<>();
        }
    }

    /* --------------------------------------- PARSE SUB CATEGORIES JSON --------------------------------------- */
    public  List<GetSubCategoriesRes> parseSubCategories(JSONArray subCategoriesArray){
        try {
            List<GetSubCategoriesRes> res = new ArrayList<>();

            for (int i = 0; i < subCategoriesArray.length(); i++) {
                GetSubCategoriesRes data = new GetSubCategoriesRes();

                JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                data.setName(subCategoryObj.getString(Constants.CMS_TITLE));

                JSONObject contentObj = subCategoryObj.getJSONObject(Constants.CMS_CONTENT);
                Float rating = (Float) contentObj.getFloat("rating");
                JSONObject resolutionsObj = contentObj.getJSONObject(Constants.CMS_RESOLUTIONS);

                String low = resolutionsObj.getJSONObject(Constants.CMS_LOW_RESOLUTION).getString(Constants.CMS_URL);
                String mid = resolutionsObj.getJSONObject(Constants.CMS_MID_RESOLUTION).getString(Constants.CMS_URL);
                String high = resolutionsObj.getJSONObject(Constants.CMS_HIGH_RESOLUTION).getString(Constants.CMS_URL);

                var imgData = new ImageResolutionPojo(low, mid, high);
                data.setThumbUrl(imgData);
                data.setFullpreviewUrl(imgData);
                data.setRating(rating);

                res.add(data);
            }
            return res;
        } catch (Exception e) {
            log.error("-- ERROR: [parseSubCategories] : ", e);
            return new ArrayList<>();
        }
    }
}
