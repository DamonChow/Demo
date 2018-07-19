package com.damon.yundun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/** 
 * 描述： 网易云盾检查文本或者图文记录
 * 
 * @author damon
 * @since 2018-07-02 14:29:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YunDunCheckRecord implements Serializable {
    private Integer id;

    /** 
     * 用户id
     */
    private Integer userId;

    /** 
     * 业务类型
     */
    private String businessType;

    /** 
     * 图片七牛key检测
     */
    private String images;

    /** 
     * ip地址
     */
    private String ip;

    /** 
     * 设备类型
     */
    private Integer deviceType;

    /** 
     * 设备id
     */
    private String deviceId;

    /** 
     * 结果
     */
    private Integer code;

    /** 
     * 结果描述
     */
    private String msg;

    /** 
     * 检测是否通过  1通过 2不通过
     */
    private Integer status;

    /** 
     * 检测到有问题的图片七牛key
     */
    private String confirmImages;

    /** 
     * 提交时间
     */
    private Date createTime;

    /** 
     * 所花时间 单位毫秒
     */
    private Long useTime;

    /** 
     * 文本检测
     */
    private String content;

    /** 
     * 请求
     */
    private String request;

    /** 
     * 结果
     */
    private String response;

    private static final long serialVersionUID = 1L;
}