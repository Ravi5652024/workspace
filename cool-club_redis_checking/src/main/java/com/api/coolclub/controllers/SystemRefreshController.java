package com.api.coolclub.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.coolclub.config.GeneralConfig;

import springfox.documentation.annotations.ApiIgnore;

/*
 * @Author Rohan_Sharma
*/

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/refresh")
@ApiIgnore
public class SystemRefreshController {
    private static final Logger log = LoggerFactory.getLogger(SystemRefreshController.class);

    private final GeneralConfig generalConfig;
    public SystemRefreshController(GeneralConfig generalConfig){
        this.generalConfig = generalConfig;
    }

    
    /* ------------------------------------- REFRESH : GLOBAL VARIABLE ------------------------------------- */
    @GetMapping("/global")
    public String refreshScreens() {
        try {
            generalConfig.setUpGlobalVariable();
            return "REFRESH DONE: GLOBAL VARIABLE";
        } catch (Exception e) {
            log.error("-- ERROR : SETUP: GLOBAL VARIABLE ",e);
            return "REFRESH FAILED: GLOBAL VARIABLE";
        }
    }

    /* ------------------------------------- REFRESH : OPERATOR DATA ------------------------------------- */
    @GetMapping("/operator")
    public String refreshScreensAssets() {
        try {
            generalConfig.setUpOperators();
            return "REFRESH DONE: OPERATOR DATA";
        } catch (Exception e) {
            log.error("-- ERROR : SETUP: OPERATOR DATA ",e);
            return "REFRESH FAILED: OPERATOR DATA";
        }
    }
}
