package com.damon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/1 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TianXingIdCard extends BaseTianXingData {


    /**
     * success : true
     * requestOrder : XXX
     * data : {"name":"XXX","identityCard":"XXX","compareStatus":"SAME","compareStatusDesc":"一致"}
     */

    private Data data;

    @lombok.Data
    @lombok.AllArgsConstructor
    @lombok.NoArgsConstructor
    public static class Data {
        /**
         * name : XXX
         * identityCard : XXX
         * compareStatus : SAME
         * compareStatusDesc : 一致
         */

        //姓名
        private String name;

        //身份证号码
        private String identityCard;

        //比对结果(SAME、DIFFERENT、NO_DATA)
        private String compareStatus;

        //结果说明(一致、不一致、无数据)
        private String compareStatusDesc;

    }
}
