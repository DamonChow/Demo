package com.damon.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：登录凭证校验
 *
 * @author zhoujiwei
 * @since 2018/3/28 下午8:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatSession extends WechatBase {


    private static final long serialVersionUID = -4767493167725221298L;
    /**
     * openid : OPENID
     * session_key : SESSIONKEY
     * unionid : UNIONID
     */

    @SerializedName("openid")
    private String openId;

    @SerializedName("session_key")
    private String sessionKey;

    @SerializedName("unionid")
    private String unionId;

}
