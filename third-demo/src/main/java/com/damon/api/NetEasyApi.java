package com.damon.api;

import com.damon.yundun.model.ImageCheckResponse;
import com.damon.yundun.model.TextCheckResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * 功能：网易api
 *
 * @author Damon Chow
 * @since 2018/6/26 14:39
 */
public interface NetEasyApi {

    @POST("/v3/text/check")
    @FormUrlEncoded
    Call<TextCheckResponse> textCheck(@FieldMap Map<String, String> map);

    @POST("/v3/image/check")
    @FormUrlEncoded
    Call<ImageCheckResponse> imageCheck(@FieldMap Map<String, String> map);


}
