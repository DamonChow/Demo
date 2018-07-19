package com.damon.api;

import com.damon.model.TianXingAccessToken;
import com.damon.model.TianXingIdCard;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 功能：天行数科接口
 *
 * @author Damon Chow
 * @since 2018/6/1 11:49
 */
public interface TianXingShuKeApi {

    @RequestLine("POST /api/rest/common/organization/auth?account={account}&signature={signature}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    TianXingAccessToken getAccessToken(@Param("account") String account,
                                       @Param("signature") String signature);

    /**
     * 校验身份证
     *
     * @param account       机构名称
     * @param accessToken   授权码
     * @param name          姓名
     * @param idCard        身份证号码
     * @param mockType      模拟标识，本接口可传入 SAME、DIFFERENT、 NO_DATA。
     * <note>
     *    mockType(模拟标识)仅在测试环境生效，且传入 mockType 后返回数 据均为模拟数据。
     *    并且通过此种方式调用接口不扣除测试条数。
     *    建议对接接口 时使用此方式对接。
     * </note>
     * @return
     */
    @RequestLine("GET /api/rest/police/identity?account={account}&accessToken={accessToken}&name={name}&idCard={idCard}&mockType={mockType}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    TianXingIdCard checkIdentity(@Param("account") String account,
                                 @Param("accessToken") String accessToken,
                                 @Param("name") String name,
                                 @Param("idCard") String idCard,
                                 @Param("mockType") String mockType);

}