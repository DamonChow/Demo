package com.damon.yundun.model;

import com.ywxk.bitell.third.common.YunDunCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/7/4 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse implements Serializable{

    private static final long serialVersionUID = -5662849244739805268L;

    /**
     * 接口调用状态，200:正常，其他值：调用出错，
     * @see YunDunCodeEnum
     */
    private int code;

    //结果说明，如果接口调用出错，那么返回错误描述，成功返回 ok
    private String msg;
}
