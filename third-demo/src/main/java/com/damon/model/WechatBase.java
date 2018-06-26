package com.damon.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author zhoujiwei
 * @since 2018/3/28 下午8:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatBase implements Serializable {

    private static final long serialVersionUID = 6944035750659971109L;
    /**
     * errcode : 40029
     * errmsg : invalid code
     */

    @SerializedName("errcode")
    private int errorCode;

    @SerializedName("errmsg")
    private String errorMsg;

}