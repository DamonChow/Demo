package com.damon;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/1 15:07
 */
public class FeginUtil {

    public static <T> T getClient(Class<T> clazz, String url) {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(clazz, url);
    }
}
