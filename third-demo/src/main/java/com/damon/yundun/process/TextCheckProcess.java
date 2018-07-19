package com.damon.yundun.process;

import com.damon.ApiResponse;
import com.damon.ThirdConstants;
import com.damon.yundun.YunDunCheckRecord;
import com.damon.yundun.model.TextCheckRequest;
import com.damon.yundun.model.TextCheckResponse;
import com.damon.yundun.model.TextResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 功能：文本检测进程
 *
 * @author Damon Chow
 * @since 2018/7/4 10:48
 */
@Slf4j
public class TextCheckProcess extends AbstractCheckProcess<TextCheckResponse, TextCheckRequest> {

    @Override
    protected TextCheckResponse execute(Map<String, String> params) {
        TextCheckResponse response = null;
        try {
            Call<TextCheckResponse> call = netEasyApi.textCheck(params);
            Response<TextCheckResponse> execute = call.execute();
            response = execute.body();
        } catch (IOException e) {
            log.error("异常：", e);
        }
        return response;
    }

    @Override
    protected boolean isSwitchOpen() {
        return true;
    }

    /**
     * 初始文本检测参数
     *
     * @param params
     * @param request
     */
    @Override
    public void initParam(Map<String, String> params, TextCheckRequest request) {
        params.put("content", request.getContent());
        String deviceId = request.getDeviceId();
        if (StringUtils.isNotBlank(deviceId)) {
            params.put("deviceId", deviceId);
        }
        Integer deviceType = request.getDeviceType();
        if (Objects.nonNull(deviceType)) {
            params.put("deviceType", deviceType.toString());
        }
        String ip = request.getIp();
        if (StringUtils.isNotBlank(ip)) {
            params.put("ip", ip);
        }
        Integer userId = request.getUserId();
        if (Objects.nonNull(userId)) {
            params.put("account", userId.toString());
        }
        params.put("dataId", UUID.randomUUID().toString());

        params.put("callback", UUID.randomUUID().toString());
        params.put("publishTime", String.valueOf(System.currentTimeMillis()));
    }

    @Override
    protected String getApiVersion() {
        return "textVersion";
    }

    @Override
    protected YunDunCheckRecord initRecord(Map<String, String> params,
                                           TextCheckRequest request,
                                           TextCheckResponse response) {
        String requestText = new Gson().toJson(params);
        String responseText = new Gson().toJson(response);

        YunDunCheckRecord record = YunDunCheckRecord.builder()
                .userId(request.getUserId())
                .businessType(request.getBusinessType())
                .deviceId(request.getDeviceId())
                .deviceType(request.getDeviceType())
                .ip(request.getIp())
                .content(request.getContent())
                .request(requestText)
                .response(responseText)
                .code(response.getCode())
                .msg(response.getMsg())
                .build();
        return record;
    }

    @Override
    protected ApiResponse handleCheckResult(TextCheckResponse response,
                                            YunDunCheckRecord record) {
        TextCheckResponse.ResultBean resultBean = response.getResult();
        int action = Optional.ofNullable(resultBean)
                .map(r -> r.getAction())
                .orElse(ThirdConstants.ACTION_FAIL);

        boolean suspicion = action == ThirdConstants.ACTION_SUSPICION;
        //通过，嫌疑也视为通过
        boolean pass = action == ThirdConstants.ACTION_PASS || suspicion;
        handleRecord(record, pass);

        TextResponse result = TextResponse.builder()
                .success(pass)
                .suspicion(suspicion)
                .build();
        return ApiResponse.builder().success(true).t(result).build();
    }

    private void handleRecord(YunDunCheckRecord record, boolean pass) {
        //检测记录状态：1通过 2不通过
        int status = pass ? 1 : 2;
        record.setStatus(status);
    }
}
