package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/7/4 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicRequest implements Serializable {

    private static final long serialVersionUID = 6074191126351006679L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户IP地址
     */
    private String ip;

    /**
     * 云盾请求类型
     */
    private String businessType;
}
