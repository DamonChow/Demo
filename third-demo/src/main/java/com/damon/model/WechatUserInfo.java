package com.damon.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author zhoujiwei
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

    @ApiModelProperty("用户openId")
    private String openId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别，值为1时是男性，值为2时是女性，值为0时是未知")
    private String gender;

    @ApiModelProperty("用户所在城市")
    private String city;

    @ApiModelProperty("用户所在省份")
    private String province;

    @ApiModelProperty("用户所在国家")
    private String country;

    @ApiModelProperty("用户头像")
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