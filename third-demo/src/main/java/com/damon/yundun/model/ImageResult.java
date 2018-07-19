package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：图片检测不通过
 *
 * @author Damon Chow
 * @since 2018/6/29 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResult implements Serializable {

    private static final long serialVersionUID = 8728722047349501653L;

    /**
     * 图片七牛key
     */
    private String name;

    /**
     * 是否含有不确定检测
     */
    private boolean notConfirm = false;

    /**
     * 图片检测不通过原因列表
     * (100：色情，110：性感，200：广告，210：二维码，300：暴恐，400：违禁，500：涉政)
     */
    private List<ImageDetailResult> labels;
}
