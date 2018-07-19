package com.damon.yundun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/29 09:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextCheckResponse extends BasicResponse {

    private static final long serialVersionUID = 7095546016528185922L;

    /**
     * code : 200
     * msg : ok
     * result : {"taskId":"079560a6c9f34783bdce47e168510038","action":2,"labels":[{"label":100,"level":2,"details":{"hint":["xxx","ooo"]}}]}
     */

    //接口返回结果，各个接口自定义，若为空标识返回正常
    private ResultBean result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultBean {
        /**
         * taskId : 079560a6c9f34783bdce47e168510038
         * action : 2
         * labels : [{"label":100,"level":2,"details":{"hint":["xxx","ooo"]}}]
         */

        //本次请求数据标识，可以根据该标识查询数据最新结果
        private String taskId;

        //检测结果，0：通过，1：嫌疑，2：不通过
        private int action;

        //分类信息
        private List<LabelBean> labels;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class LabelBean {
            /**
             * label : 100
             * level : 2
             * details : {"hint":["xxx","ooo"]}
             */
            //分类信息，100：色情，200：广告，400：违禁，500：涉政，600：谩骂，700：灌水
            private int label;

            //分类级别，1：不确定，2：确定
            private int level;

            private DetailBean details;

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class DetailBean {

                //线索信息，用于定位文本中有问题的部分，辅助人工审核
                private List<String> hint;

            }
        }
    }
}
