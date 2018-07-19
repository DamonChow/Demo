package com.damon.yundun.process;

import com.damon.ApiResponse;
import com.damon.Service.YunDunCheckRecordService;
import com.damon.ThirdConstants;
import com.damon.YunDunCodeEnum;
import com.damon.api.NetEasyApi;
import com.damon.yundun.YunDunCheckRecord;
import com.damon.yundun.model.BasicRequest;
import com.damon.yundun.model.BasicResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 功能：抽象检测进程
 *
 * @author Damon Chow
 * @since 2018/7/4 10:48
 */
@Slf4j
public abstract class AbstractCheckProcess<RS extends BasicResponse, RQ extends BasicRequest> {

    protected NetEasyApi netEasyApi;

    protected YunDunCheckRecordService recordService;

    private String secretId = "secretId";
    private String secretKey = "secretKey";

    /**
     * 具体查询
     *
     * @param params 请求参数
     * @return
     */
    protected abstract RS execute(Map<String, String> params);

    /**
     * 是否打开检测请求
     *
     * @return
     */
    protected abstract boolean isSwitchOpen();

    /**
     * 初始化接口特定请求参数
     *
     * @param params
     * @param request
     */
    protected abstract void initParam(Map<String, String> params, RQ request);

    /**
     * 获取接口版本
     *
     * @return
     */
    protected abstract String getApiVersion();

    /**
     * 初始化检测记录
     *
     * @param params
     * @param request
     * @param response
     * @return
     */
    protected abstract YunDunCheckRecord initRecord(Map<String, String> params,
                                                    RQ request, RS response);

    /**
     * 处理检测结果
     *
     * @param response 结果
     * @param record   记录
     * @return
     */
    protected abstract ApiResponse handleCheckResult(RS response, YunDunCheckRecord record);

    /**
     * 检测
     *
     * @param request
     * @return
     */
    public ApiResponse execute(RQ request) {
        if (!isSwitchOpen()) {
            log.info("关闭检验！");
            return ApiResponse.builder().success(true).build();
        }

        // 1.请求参数
        Map<String, String> params = initParam(request);

        // 2.检测
        long begin = System.currentTimeMillis();
        log.info("开始检测，检测类型|{}|请求|{}|", request.getBusinessType(),
                new Gson().toJson(params));
        RS response = execute(params);
        log.info("检测完成，检测类型|{}|响应|{}|", request.getBusinessType(),
                new Gson().toJson(response));
        long end = System.currentTimeMillis();

        // 3.检测记录
        YunDunCheckRecord record = initRecord(params, request, response);
        record.setUseTime(end - begin);

        // 4.校验是否正常返回
        if (!isOk(response.getCode())) {
            saveRecord(record);
            return ApiResponse.builder().success(false).msg("失败,请重试").build();
        }

        // 5.获取检测结果并设置检测结果到记录中
        ApiResponse result = handleCheckResult(response, record);
        saveRecord(record);
        return result;
    }

    /**
     * 是否正常返回
     *
     * @param code
     * @return
     */
    private boolean isOk(int code) {
        if (code == YunDunCodeEnum.OK.getCode()) {
            return true;
        }

        YunDunCodeEnum codeEnum = YunDunCodeEnum.getCodeEnum(code);
        log.error("检测失败：{}" + codeEnum);
        return false;
    }

    /**
     * 保存校测记录
     *
     * @param record
     */
    protected void saveRecord(YunDunCheckRecord record) {
        recordService.saveRecord(record);
    }

    /**
     * 初始化检测请求
     *
     * @param request
     * @return
     */
    private Map<String, String> initParam(RQ request) {
        Map<String, String> params = new HashMap();

        // 1.设置公共参数
        initPublicParam(params, getApiVersion());
        initBusinessIdParam(params, request.getBusinessType());

        // 2.设置私有参数
        initParam(params, request);

        // 3.生成签名信息
        String signature = genSignature(params);
        params.put("signature", signature);
        return params;
    }

    /**
     * 初始公共参数
     *
     * @param params
     */
    protected void initPublicParam(Map<String, String> params, String version) {
        params.put("secretId", secretId);
        params.put("version", version);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));
    }

    /**
     * 设置业务id
     *
     * @param params
     * @param businessType 业务类型 USER_TEXT,
     */
    protected void initBusinessIdParam(Map<String, String> params, String businessType) {
        String businessId = null;
        //TODO
        switch (businessType) {
            case ThirdConstants.YUNDUN_CHECK_IMAGE:
                businessId = "普通图片业务id";
                break;
            case ThirdConstants.YUNDUN_CHECK_TEXT:
                businessId = "普通文本业务id";
                break;
            case ThirdConstants.YUNDUN_CHECK_USER_IMAGE:
                businessId = "用户资料图片业务id";
                break;
            case ThirdConstants.YUNDUN_CHECK_USER_TEXT:
                businessId = "用户资料文本业务id";
                break;
            default:
                break;
        }
        params.put("businessId", businessId);
    }

    /**
     * 生成签名信息
     *
     * @param params 接口请求参数名和参数值map，不包括signature参数名
     * @return
     */
    protected String genSignature(Map<String, String> params) {
        // 1. 参数名按照ASCII码表升序排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 2. 按照排序拼接参数名与参数值
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(params.get(key));
        }
        // 3. 将secretKey拼接到最后
        sb.append(secretKey);

        // 4. MD5是128位长度的摘要算法，转换为十六进制之后长度为32字符
        try {
            return DigestUtils.md5Hex(sb.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("生成签名失败：参数|{}|错误|{}", params, e);
        }
        return null;
    }
}
