package com.damon.yundun.process;

import com.damon.ApiResponse;
import com.damon.ThirdConstants;
import com.damon.YunDunImageLabelEnum;
import com.damon.yundun.YunDunCheckRecord;
import com.damon.yundun.model.ImageCheckRequest;
import com.damon.yundun.model.ImageCheckResponse;
import com.damon.yundun.model.ImageDetailResult;
import com.damon.yundun.model.ImageResponse;
import com.damon.yundun.model.ImageResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 功能：图片检测进程
 *
 * @author Damon Chow
 * @since 2018/7/4 10:48
 */
@Slf4j
public class ImageCheckProcess extends AbstractCheckProcess<ImageCheckResponse, ImageCheckRequest> {

    @Override
    protected ImageCheckResponse execute(Map<String, String> params) {
        ImageCheckResponse response = null;
        try {
            Call<ImageCheckResponse> call = netEasyApi.imageCheck(params);
            Response<ImageCheckResponse> execute = call.execute();
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

    @Override
    protected void initParam(Map<String, String> params, ImageCheckRequest request) {
        // 传图片url进行检测，name结构产品自行设计，用于唯一定位该图片数据
        //http://support.dun.163.com/documents/2018041902?docId=150429557194936320
        JsonArray jsonArray = new JsonArray();
        List<String> imageKeyList = request.getImageKeyList();
        imageKeyList.forEach(key -> {
            JsonObject image = new JsonObject();
            //图片名称(或图片标识)， 该字段为回调信号字段，产品可以根据业务情况自行设计，如json结构、或者为图片url均可
            image.addProperty("name", key);
            //类型，分别为1：图片URL，2:图片BASE64值
            image.addProperty("type", 1);
            //图片内容，如type=1，则该值为图片URL，如type=2，则该值为图片BASE64值。图片URL检测单次请求最多支持32张，图片BASE64值检测单次请求大小限制为最多10MB
            //TODO 组装图片路径
//            image.addProperty("data", qnService.getResourceUrl(key));
            jsonArray.add(image);
        });
        params.put("images", jsonArray.toString());
        Integer userId = request.getUserId();
        if (Objects.nonNull(userId)) {
            params.put("account", userId.toString());
        }
        String ip = request.getIp();
        if (StringUtils.isNotBlank(ip)) {
            params.put("ip", ip);
        }
    }

    @Override
    protected String getApiVersion() {
        return "imageVersion";
    }

    @Override
    protected YunDunCheckRecord initRecord(Map<String, String> params, ImageCheckRequest request, ImageCheckResponse response) {
        String requestText = new Gson().toJson(params);
        String responseText = new Gson().toJson(response);

        YunDunCheckRecord record = YunDunCheckRecord.builder()
                .userId(request.getUserId())
                .businessType(request.getBusinessType())
                .ip(request.getIp())
                .images(StringUtils.join(request.getImageKeyList(), ","))
                .request(requestText)
                .response(responseText)
                .code(response.getCode())
                .msg(response.getMsg())
                .build();
        return record;
    }

    @Override
    protected ApiResponse handleCheckResult(ImageCheckResponse response,
                                            YunDunCheckRecord record) {
        ImageResponse imageResponse = new ImageResponse();
        List<ImageCheckResponse.ResultBean> resultList = response.getResult();
        if (CollectionUtils.isEmpty(resultList)) {
            return ApiResponse.builder().success(true).t(imageResponse).build();
        }

        //获取不通过结果
        List<ImageResult> list = resultList.stream()
                .map(result -> getImageResult(result))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        imageResponse.setList(list);
        imageResponse.setSuccess(CollectionUtils.isEmpty(list));
        long notConfirmCount = list.stream().filter(r -> r.isNotConfirm()).count();
        imageResponse.setSuspicion(notConfirmCount > 0L);
        handleRecord(record, imageResponse, list);
        return ApiResponse.builder().success(true).t(imageResponse).build();
    }

    /**
     * 解析图片检测结果
     * <note>
     * 如果为返回空，则便是检测通过，返回对象则表示图片检测不过
     * </note>
     *
     * @param resultBean
     * @return
     */
    private ImageResult getImageResult(ImageCheckResponse.ResultBean resultBean) {
        List<ImageCheckResponse.ResultBean.LabelsBean> labels = resultBean.getLabels();
        //只检测已经确定了和不确定的为不正常）
        List<ImageDetailResult> details = labels.stream()
                .filter(l -> Objects.equals(l.getLevel(), ThirdConstants.LEVEL_CONFIRM)
                        || Objects.equals(l.getLevel(), ThirdConstants.LEVEL_NOT_CONFIRM))
                .map(l -> {
                    int label = l.getLabel();
                    int level = l.getLevel();
                    String desc = YunDunImageLabelEnum.getLabelDesc(label);
                    return new ImageDetailResult(label, level, desc);
                })
                .collect(Collectors.toList());

        long notConfirmCount = details.stream()
                .filter(r -> Objects.equals(r.getLevel(), ThirdConstants.LEVEL_NOT_CONFIRM))
                .count();

        ImageResult result = ImageResult.builder()
                .name(resultBean.getName())
                .labels(details)
                .notConfirm(notConfirmCount > 0L)
                .build();
        return result;
    }

    private void handleRecord(YunDunCheckRecord record, ImageResponse imageResponse, List<ImageResult> list) {
        int status = imageResponse.isSuccess() ? 1 : 2;
        record.setStatus(status);
        String confirmImages = list.stream()
                .map(ImageResult::getName)
                .collect(Collectors.joining(","));
        record.setConfirmImages(confirmImages);
    }
}
