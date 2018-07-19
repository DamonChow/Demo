package com.damon;

/**
 * 功能：
 *
 * @author damon
 * @since 2018/6/29 09:37
 */
public class ThirdConstants {

    public static final String YUNDUN_CHECK_USER_TEXT = "USER_TEXT";

    public static final String YUNDUN_CHECK_USER_IMAGE = "USER_IMAGE";

    public static final String YUNDUN_CHECK_TEXT = "TEXT";

    public static final String YUNDUN_CHECK_IMAGE = "IMAGE";

    //文本检测：0：通过
    public static final int ACTION_PASS = 0;

    //文本检测：1：嫌疑
    public static final int ACTION_SUSPICION = 1;

    //文本检测：2：不通过
    public static final int ACTION_FAIL = 2;

    //图文检测分类级别，0：正常
    public static final int LEVEL_NORMAL = 0;

    //图文检测分类级别，1：不确定
    public static final int LEVEL_NOT_CONFIRM = 1;

    //图文检测分类级别，2：确定（确定有问题）
    public static final int LEVEL_CONFIRM = 2;

}