package com.damon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/29 09:51
 */
@Getter
@AllArgsConstructor
public enum YunDunCodeEnum {

    OK(200, "接口调用成功"),
    BAD_REQUEST(400, "请求缺少 secretId 或 businessId"),
    FORBIDDEN(401, "businessId无效或过期"),
    NOT_FOUND(404, "业务配置不存在"),
    PARAM_ERROR(405, "请求参数异常"),
    SIGNATURE_FAILURE(410, "签名验证失败，请重新参考demo签名代码"),
    HIGH_FREQUENCY(411, "请求频率或数量超过限制"),
    PARAM_LEN_OVER_LIMIT(414, "请求长度超过限制"),
    TARGET_VERSION_LIMIT(415, "业务版本限制，如无人工服务版本，无法请求离线结果获取接口"),
    IMAGE_DOWNLOAD_FAILURE(416, "图片下载失败"),
    REQUEST_EXPIRED(420, "请求过期"),
    REPLAY_ATTACK(430, "重放攻击"),
    SERVICE_UNAVAILABLE(503, "接口异常"),;

    private int code;

    private String msg;

    public static YunDunCodeEnum getCodeEnum(int code) {
        return Stream.of(values())
                .filter(e-> Objects.equals(e.getCode(), code))
                .findFirst()
                .orElse(null);
    }
}
