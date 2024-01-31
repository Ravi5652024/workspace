package com.api.coolclub.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author Rohan_Sharma
*/

@Document(collection = "otp_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpHistory {
    @Id
    private String id;
    @Indexed(unique = true)
    private String userName;
    private String otp;
    private long creationTime;

    public OtpHistory(String userName, String otp, long creationTime) {
        this.userName = userName;
        this.otp = otp;
        this.creationTime = creationTime;
    }
}
