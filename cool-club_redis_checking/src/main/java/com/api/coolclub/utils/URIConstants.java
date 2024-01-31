package com.api.coolclub.utils;

/*
 * @Author Rohan_Sharma
*/

public class URIConstants {
    
    private URIConstants(){
        
    }

    public static final String API_VERSION =  "/v1";

    /*
     *  Default Controller Constants
    */
    
    public static final String DEFAULT_BASE_URL =  "/api";
    public static final String GET_SCREEN_VERSION =  "/screens_version";
    public static final String GET_CONTENT =  "/content";
    public static final String GET_STARTUP_SCREEN =  "/startup_screen/{screenType}";

    /*
     *  User Profile Controller Constants
    */

    public static final String USER_BASE_URL =  "/user";
    public static final String USER_REGISTER =  "/register";
    public static final String USER_LOGIN =  "/login";
    public static final String USER_PROFILE =  "/profile/{userId}";

    /*
     *  OTP Controller Constants
    */
    public static final String OTP_BASE_URL =  "/otp";
    public static final String OTP_SEND =  "/send/{userName}";
    public static final String OTP_VERIFY =  "/verify";

    /*
     * Screens Controller Constants
    */
    public static final String SCREENS_BASE_URL =  "/screen";
    public static final String GET_SCREEN =  "/{screenType}";
    public static final String GET_SUB_CATEGORIES =  "/sub_categories";

    /*
     * Operator Controller Constants
    */

    public static final String OPERATOR_BASE_URL =  "/operator";
    public static final String ADD_OPERATOR =  "/add";
    public static final String GET_OPERATOR =  "/get";
    public static final String UPDATE_OPERATOR =  "/update";

    /*
     *  SUBSCRIPTION Controller Constants
    */
    
    public static final String SUBSCRIPTION_BASE_URL =  "/subscription";
    public static final String SUBSCRIBE =  "/subscribe";
    public static final String CHECK_SUBSCRIPTION =  "/check_subscription/{userId}";
}
