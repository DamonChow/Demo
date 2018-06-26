package com.damon.Service;

import com.damon.FeginUtil;
import com.damon.api.TianXingShuKeApi;
import com.damon.model.TianXingAccessToken;
import com.damon.model.TianXingIdCard;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 功能：
 *
 * @author zhoujiwei
 * @since 2018/6/1 15:21
 */
@Slf4j
public class TianXingShuKeService {

    private String url = "url";
    private String account = "account";
    private String signature = "signature";
    private String mockType = "mockType";

    private String SAME = "SAME";

    private String DIFFERENT = "DIFFERENT";

    private String NO_DATA = "NO_DATA";

    private String TOKEN_KEY = "tianxingshuke:token";

    private String CHECK_KEY = "tianxingshuke:checkIdCard:userId:{0}";

    private TianXingShuKeApi getApi() {
        return FeginUtil.getClient(TianXingShuKeApi.class, url);
    }

    public String getAccessToken() {
        //获取api
        TianXingShuKeApi api = getApi();
        //获取accessToken
        TianXingAccessToken result = api.getAccessToken(account, signature);
        if (!result.isSuccess()) {
            log.error("获取token失败, {}", result);
            return null;
        }
        String accessToken = Optional.of(result).map(t -> t.getData()).map(d -> d.getAccessToken()).orElse(null);
        Long expireTime = Optional.of(result).map(t -> t.getData()).map(d -> d.getExpireTime()).orElse(null);
        if (StringUtils.isBlank(accessToken)) {
            log.error("获取token失败,没有获取到accessToken, {}", result);
            return null;
        }

        return accessToken;
    }

    /**
     * 身份证校验
     *
     * @param name
     * @param idCard
     * @return
     */
    public boolean checkIdCard(Integer userId, String name, String idCard) {
        //是否禁用校验
        /*if (properties.isDisabled()) {
            log.info("关闭身份证校验。");
            return true;
        }*/

        //检查用户一天校验次数
//        if (properties.isUserLimitSwitch() && greaterThanUserLimit(userId)) {
//            return false;
//        }

        Integer retry = 3;
        //循环retry(默认3)次，有可能出现天行数科token服务器时间与本地服务器时间不同步,而失效情况
        int index = 0;
        while (index < retry) {
            String accessToken = getAccessToken();

            try {
                TianXingShuKeApi api = getApi();
                TianXingIdCard result = api.checkIdentity(account, accessToken, name, idCard, mockType);
                log.info("姓名|{}|身份证号|{}|校验成功, 结果={}", name, idCard, result);


                if (!result.isSuccess()) {
                    log.error("姓名|{}|身份证号|{}|校验失败1, {}", name, idCard, result);
                    return false;
                }

                String status = Optional.of(result)
                        .map(r -> r.getData())
                        .map(d -> d.getCompareStatus())
                        .orElse(NO_DATA);
                if (StringUtils.equals(status, SAME)) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                log.error("姓名|{}|身份证号|{}|校验失败2, {}", name, idCard, e);
            }

            index++;
        }
        return false;
    }

    /*   *//**
     * 当天单用户校验次数
     *
     * @param userId
     * @return
     *//*
    private boolean greaterThanUserLimit(Integer userId) {
        String key = MessageFormat.format(CHECK_KEY, userId);
        ValueOperations opsForValue = redisTemplate.opsForValue();
        Long userLimit = Optional.ofNullable(properties.getUserLimit())
                .orElse(5L);
        Long usedLimit = Optional.ofNullable(opsForValue.get(key))
                .map(o -> ((Integer) o).longValue())
                .orElse(0L);
        if (usedLimit >= userLimit) {
            return true;
        }

        opsForValue.increment(key, 1L);
        redisTemplate.expire(key, DateUtil.getLastSeconds(), TimeUnit.SECONDS);
        return false;
    }*/

}