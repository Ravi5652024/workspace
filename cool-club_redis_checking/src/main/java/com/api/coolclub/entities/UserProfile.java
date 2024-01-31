package com.api.coolclub.entities;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.api.coolclub.enums.GenderEnum;
import com.api.coolclub.enums.UserProfileStatusEnum;
import com.api.coolclub.models.pojo.UserSubscriptionDetailsPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author Rohan_Sharma
*/

@Document(collection = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    
    @Id
    private String id;

    @Indexed(unique = true, sparse = true)
    private String nickName;

    @Indexed(unique = true, sparse = true)
    private String email;

    @Indexed(unique = true, sparse = true)
    private String msisdn;

    private String profilePic;
    private Long age;
    private String deviceType;
    private String deviceId;

    private String remainingDiamonds;

    @Indexed
    private GenderEnum gender;

    @Indexed
    private UserProfileStatusEnum status;

    private UserSubscriptionDetailsPojo subDetails;

    @CreatedDate
    private Instant createdOn;

    @LastModifiedDate
    private Instant modifiedOn;


    private String language;
    private String bio;
    private String preferences;
    private String hobbies;
    private String password;

    public UserProfile(String deviceType, String deviceId, UserProfileStatusEnum status) {
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.status = status;
    }
}
