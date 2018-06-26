package com.damon.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 *
 * @author zhoujiwei
 * @since 2018/3/28 下午1:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatToken extends WechatBase{

    private static final long serialVersionUID = 1298366061883355492L;

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     */

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expiresIn;

}
