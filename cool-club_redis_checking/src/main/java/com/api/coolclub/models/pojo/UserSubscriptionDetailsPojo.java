package com.api.coolclub.models.pojo;

import org.springframework.stereotype.Component;

import com.api.coolclub.enums.SubscriptionStatusEnum;

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
public class UserSubscriptionDetailsPojo {
    private SubscriptionStatusEnum status;
    private String expiry;
}
