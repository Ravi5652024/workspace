package com.api.coolclub.models.response;

import org.springframework.stereotype.Component;

import com.api.coolclub.enums.UserProfileStatusEnum;
import com.api.coolclub.models.pojo.UserSubscriptionDetailsPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author Rohan_Sharma
*/

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserProfileDetails {

    private String id;
    private String nickName;
    private String email;
    private String msisdn;
    private Long age;
    private String profilePic;
    private String gender;
    private UserProfileStatusEnum status;
    private UserSubscriptionDetailsPojo subStatus;

    // private String bio;
    // private String preferences;
    // private String hobbies;
    // private String language;
}
