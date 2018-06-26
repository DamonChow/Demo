package com.damon.model;

import lombok.Data;

/**
 * 功能：
 *
 * @author zhoujiwei
 * @since 2018/6/1 15:26
 */
@Data
public class BaseTianXingData {

    //请求状态(是否成功)
    private boolean success;

    //唯一订单号
    private String requestOrder;

    //异常码(接口异常时返回)
    private String code;

    //异常说明(接口异常时返回)
    private String error;

    //异常说明(中文)(接口异常时返回)
    private String errorDesc;

}
