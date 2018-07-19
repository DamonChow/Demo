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
public enum YunDunImageLabelEnum {

    SEXY(100, "色情"),

    SEX(110, "性感"),

    AD(200, "广告"),

    QR(210, "二维码"),

    VIOLENT(300, "暴恐"),

    CONTRABAND(400, "违禁"),

    GOVT(500, "涉政"),
    ;

    private int label;

    private String desc;

    public static YunDunImageLabelEnum getEnum(int label) {
        return Stream.of(values())
                .filter(e-> Objects.equals(e.getLabel(), label))
                .findFirst()
                .orElse(null);
    }

    public static String getLabelDesc(int label) {
        return Stream.of(values())
                .filter(e-> Objects.equals(e.getLabel(), label))
                .findFirst()
                .map(e->e.getDesc())
                .orElse(null);
    }


}
