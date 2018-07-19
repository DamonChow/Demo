package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/29 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDetailResult implements Serializable {

    private static final long serialVersionUID = 8728722047349501653L;

    /**
     * 原因标识：100：色情，110：性感，200：广告，210：二维码，300：暴恐，400：违禁，500：涉政
     */
    private Integer label;

    //分类级别，0：正常，1：不确定，2：确定
    private int level;

    /**
     * 原因描述：100：色情，110：性感，200：广告，210：二维码，300：暴恐，400：违禁，500：涉政
     */
    private String desc;
}
