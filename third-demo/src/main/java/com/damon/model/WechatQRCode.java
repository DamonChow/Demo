package com.damon.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/4/9 11:37
 */
@Data
public class WechatQRCode implements Serializable {

    private static final long serialVersionUID = 6102126423682983828L;

    private String errcode;

    private String errmsg;

    private String base;

}
