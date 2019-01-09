package com.damon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BasicResultCode {

    SUCCESS(0, "success"),

    INNER_ERROR(-1, "系统异常"),
    /**
     * 100-199
     */
    SQL_ERROR(199, "sql exception"),
    /**
     * 200-299
     */
    REDIS_ERROR(299, "redis exception"),

    /**
     * 300-399 内部feign调用错误
     */
    HTTP_INNTER_READ_TIME_OUT(300, "inner time out"),
    HTTP_INNTER_CONNECTION_TIME_OUT(301, "inner connection time out"),
    HTTP_INNTER_SERVICE_NOT_AVAILABLE(302, "service not available"),
    HTTP_INNTER_ERROR(399, "http inner error!"),

    /**
     * 通用请求参数校验 10000-10099
     **/
    PARAM_VALIDATE_ERROR(10001, "参数验证失败"),
    PARAM_MISS_ERROR(10002, "参数丢失"),
    PARAM_ERROR(10099, "参数错误"),

    /**
     * http请求错误 10200-10299
     */
    HTTP_METHOD_NOT_ALLOW_ERROR(100200, "请求方法方式不对"),
    HTTP_UNSUPPORTED_MEDIA_TYPE_ERROR(100201, "不支持的媒体类型"),

    /**
     * 安全错误 10300-10399
     */
    SECURITY_ACCESS_DENIED(10300, "未授权访问!"),
    SECURITY_AUTHENTICATION(10301, "身份验证失败,请输入正确信息!"),
    SECURITY_TOKEN_NOT_ACTIVE(10302, "TOKEN 已失效，刷新token或者重新获取!"),

    BLACK_LIST_EXIST(30201, "已列入IP黑名单!"),
    SUBMIT_EXIST(30202, "请求已提交,请勿重复提交"),
    TIME_ERROR(30203, "时间错误,请校验当前时间"),
    SIGN_ERROR(30204, "签名错误"),
    SIGN_EMPTY_ERROR(30205, "请求非法");

    private int code;

    private String msg;

}
