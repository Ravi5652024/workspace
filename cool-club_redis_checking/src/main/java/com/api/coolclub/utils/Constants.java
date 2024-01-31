package com.api.coolclub.utils;

/*
 * @Author Rohan_Sharma
*/

public class Constants {
    
    // piblic Constants(){}
    
    /*
     * Generic Constants
    */

    public static final int SUCCESS_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int FORBIDDEN_CODE = 403;
    public static final int CONFLICT_CODE = 409;
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;
    public static final String SUCCESSFUL = "successful";
    public static final String UNSUCCESSFUL = "unsuccessful";
    public static final String UNAUTHORIZED_REQUEST =  "unauthorized request";
    public static final String JWT_TOKEN_EXPIRED =  "token expired";
    public static final String INVALID_JWT_TOKEN =  "invalid token";
    public static final String SERVER_ERROR =  "SERVER_ERROR";
    public static final String INVALID_PARAMETERS =  "INVALID_PARAMETERS";
    
    

    /*
     * GENERAL Constants IN DB
    */
    public static final String GLOBALIZED_MESSAGES = "globalizedMessages";
    public static final String GENDERS = "genders";
    public static final String GENERATE_JWT_API = "cms_generateJWTAPI";
    public static final String GET_SCREEN_DATA_API = "cms_getScreenDataAPI";
    public static final String GET_SUB_CATEGORIES_API = "cms_getSubCatogriesAPI";
    public static final String CMS_PAGE_SIZE = "cms_pageSize";
    public static final String MASTER_OTP = "masterOTP";

    /*
     * GENERAL Constants IN CODE_BASE
    */
    public static final String COOLCLUB_CATEGORIES = "coolclubCategories";
    public static final String COOLCLUB_CONTENT = "coolclubContent";
    public static final String CMS_PLACEHOLDERS = "placeholders";
    public static final String CMS_PLACEHOLDER = "placeholder";
    public static final String CMS_LANGUAGE = "language";
    public static final String CMS_SHORTCODE = "shortcode";
    public static final String CMS_METADATAIDS = "metadataIds";
    public static final String CMS_CONTENT = "content";
    public static final String CMS_RESOLUTIONS = "resolutions";
    public static final String CMS_LOW_RESOLUTION = "lowResolution";
    public static final String CMS_MID_RESOLUTION = "mediumResolution";
    public static final String CMS_HIGH_RESOLUTION = "highResolution";
    public static final String CMS_URL = "url";
    public static final String CMS_CATEGORIES = "categories";
    public static final String CMS_VALUE = "value";
    public static final String CMS_NAME = "name";
    public static final String CMS_NEXTSECTION = "nextSection";
    public static final String CMS_POSITION = "position";
    public static final String CMS_THUMBIMAGEMETADATA = "thumbImageMetaData";
    public static final String CMS_JWT_TOKEN = "cmsJwtToken";
    public static final String CMS_CONTENT_TYPE = "contentType";
    public static final String CMS_TITLE = "title";
    public static final String SCREEN_CONTENT = "content"; //-- db
    public static final String SCREEN_LANGUAGE = "language"; //-- db
    public static final String HOME_SCREEN_TYPE = "home"; //-- db
    public static final String PACKS_SCREEN_TYPE = "packs"; //-- db
    public static final String SIGNUP_SCREEN_TYPE = "signup"; //-- db

    /*
     *  OTP Constants
    */
    public static final String OTP_SEND_SUCCESSFULY = "OTP_SEND_SUCCESSFULY";

    /*
     *  User Profile Constants
    */
    public static final String USERNAME_AREADY_EXIST = "USERNAME_AREADY_EXIST";
    public static final String SIGNUP_SUCCESSFULY = "SIGNUP_SUCCESSFULY";
    public static final String INVALID_CRED =  "INVALID_CRED";
    public static final String LOGIN_SUCCESSFULY = "LOGIN_SUCCESSFULY";
    public static final String PROFILE_UPDATED = "PROFILE_UPDATED";
    public static final String WRONG_OTP = "WRONG_OTP";
    public static final String NICKNAME_AREADY_EXIST = "NICKNAME_AREADY_EXIST";

    /*
     *  Operator Constants
    */
    public static final String OPERATOR_EXIST = "OPERATOR_EXIST";
    public static final String OPERATOR_ADD_SUCCESSFULY = "OPERATOR_ADD_SUCCESSFULY";
    public static final String INVALID_OPERATOR_ID =  "INVALID_OPERATOR_ID";
    public static final String OPERATOR_UPDATED_SUCCESSFULY = "OPERATOR_UPDATED_SUCCESSFULY";
}
