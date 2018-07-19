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
public class TianXingAccessToken extends BaseTianXingData {

    /**
     * success : true
     * requestOrder : XXX
     * data : {"id":"XXX","accessToken":"XXX","account":"XXX","expireTime":1519538880833}
     */

    //请求成功后，返回的数据(详见“data 节点说 明”)
    private Data data;

    @lombok.Data
    @lombok.AllArgsConstructor
    @lombok.NoArgsConstructor
    public static class Data {
        /**
         * id : XXX
         * accessToken : XXX
         * account : XXX
         * expireTime : 1519538880833
         */

        private String id;

        //授权码
        private String accessToken;

        //账号
        private String account;

        //授权码失效时间(时间戳)
        private long expireTime;

    }
}
