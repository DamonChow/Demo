package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/7/4 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCheckRequest extends BasicRequest {

    private static final long serialVersionUID = -3161717753337546801L;

    /**
     * 检测的图片的七牛key集合
     */
    private List<String> imageKeyList;

}
