package com.damon.yundun.factory;

import com.damon.ThirdConstants;
import com.damon.yundun.process.AbstractCheckProcess;
import com.damon.yundun.process.ImageCheckProcess;
import com.damon.yundun.process.TextCheckProcess;

/**
 * 功能：网易云盾检测工厂
 *
 * @author Damon Chow
 * @since 2018/7/4 14:07
 */
public class YunDunCheckFactory {

    private ImageCheckProcess imageCheckProcess;

    private TextCheckProcess textCheckProcess;

    public AbstractCheckProcess getProcess(String businessType) {
        switch (businessType) {
            case ThirdConstants.YUNDUN_CHECK_IMAGE:
            case ThirdConstants.YUNDUN_CHECK_USER_IMAGE:
                return imageCheckProcess;
            case ThirdConstants.YUNDUN_CHECK_TEXT:
            case ThirdConstants.YUNDUN_CHECK_USER_TEXT:
                return textCheckProcess;
            default:
                throw new RuntimeException("请重试");
        }
    }
}
