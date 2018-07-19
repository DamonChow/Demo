package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：图片检测结果
 *
 * @author Damon Chow
 * @since 2018/6/29 11:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse implements Serializable {

    private static final long serialVersionUID = 632060148348929299L;

    /**
     * true图片检测通过，
     * false图片检测不通过，不通过图片原由如下list
     */
    private boolean success = true;

    /**
     * true检测疑似，
     * false检测非疑似
     */
    private boolean suspicion = false;

    /**
     * 图片检测不通过结果
     */
    private List<ImageResult> list;
}
