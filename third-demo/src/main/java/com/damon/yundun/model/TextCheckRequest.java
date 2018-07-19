package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/7/4 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextCheckRequest extends BasicRequest {

    private static final long serialVersionUID = -6996331512694520100L;

    /**
     * 用户发表内容，建议对内容中JSON、表情符、HTML标签、UBB标签等做过滤，只传递纯文本，以减少误判概率。
     * 请注意为了检测效果和性能，如果该字段长度超过1万字符，会截取前面1万字符进行检测和存储。
     */
    private String content;

    /**
     * 用户设备类型，1：web， 2：wap， 3：android， 4：iphone， 5：ipad， 6：pc， 7：wp
     */
    private Integer deviceType;

    /**
     * 用户设备 id
     */
    private String deviceId;
}
