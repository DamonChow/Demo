package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/29 11:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCheckResponse extends BasicResponse {

    private static final long serialVersionUID = 2524629517658497653L;

    /**
     * code : 200
     * msg : ok
     * result : [{"taskId":"8e0b9306cec744aeb7467486adfa96e2","status":0,"name":"http://xxx.net/xxx1.jpg","labels":[{"label":100,"level":0,"rate":0},{"label":110,"level":0,"rate":0},{"label":200,"level":0,"rate":0},{"label":210,"level":0,"rate":0},{"label":300,"level":0,"rate":0},{"label":400,"level":0,"rate":0},{"label":500,"level":0,"rate":0}],"details":{"ocrText":[]}},{"taskId":"t79b293083b840ffa208d5d9394f2376","status":0,"name":"http://xxx.net/xxx2.jpg","labels":[{"label":210,"level":2,"rate":0.9999},{"label":100,"level":0,"rate":0},{"label":110,"level":0,"rate":0},{"label":200,"level":0,"rate":0},{"label":300,"level":0,"rate":0},{"label":400,"level":0,"rate":0},{"label":500,"level":0,"rate":0}],"details":{"ocrText":[]}}]
     */

    private List<ResultBean> result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultBean {
        /**
         * taskId : 8e0b9306cec744aeb7467486adfa96e2
         * status : 0
         * name : http://xxx.net/xxx1.jpg
         * labels : [{"label":100,"level":0,"rate":0},{"label":110,"level":0,"rate":0},{"label":200,"level":0,"rate":0},{"label":210,"level":0,"rate":0},{"label":300,"level":0,"rate":0},{"label":400,"level":0,"rate":0},{"label":500,"level":0,"rate":0}]
         * details : {"ocrText":[]}
         */

        //图片名称(或图片标识)
        private String name;

        //本次请求数据标识，可以根据该标识查询数据最新结果
        private String taskId;

        //图片检测状态码，定义为：0：检测成功，610：图片下载失败，620：图片格式错误，630：其它
        private int status;

        //其他信息
        private Object details;

        //分类信息
        private List<LabelsBean> labels;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class LabelsBean {
            /**
             * label : 100
             * level : 0
             * rate : 0.0
             */

            //分类信息，100：色情，110：性感，200：广告，210：二维码，300：暴恐，400：违禁，500：涉政
            private int label;

            //分类级别，0：正常，1：不确定，2：确定
            private int level;

            //分数
            private double rate;

        }
    }
}
