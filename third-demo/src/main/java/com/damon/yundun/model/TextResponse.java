package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：图片检测结果
 *
 * @author Damon Chow
 * @since 2018/6/29 11:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextResponse implements Serializable {

    private static final long serialVersionUID = 632060148348929299L;
    
    /**
     * true文本检测通过，
     * false文本检测不通过
     */
    private boolean success = true;

    /**
     * true文本检测疑似，
     * false文本检测非疑似
     */
    private boolean suspicion = false;

}