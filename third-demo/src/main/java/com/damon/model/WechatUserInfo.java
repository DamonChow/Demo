package com.damon.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/3/28 下午10:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatUserInfo implements Serializable {

    private static final long serialVersionUID = 6646572186774350781L;

    /**
     * openId : OPENID
     * nickName : NICKNAME
     * gender : GENDER
     * city : CITY
     * province : PROVINCE
     * country : COUNTRY
     * avatarUrl : AVATARURL
     * unionId : UNIONID
     * watermark : {"appid":"APPID","timestamp":"TIMESTAMP"}
     */

    private String openId;

    private String nickName;

    private String gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;

    private WatermarkBean watermark;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WatermarkBean implements Serializable{

        private static final long serialVersionUID = -7736633918701437885L;

        /**
         * appid : APPID
         * timestamp : TIMESTAMP
         */

        @SerializedName("appid")
        private String appId;

        private String timestamp;

    }

}