package com.damon;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.List;

/**
 *
 */
public class App {

    public static void main(String[] args) {
        BingApi bingApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(BingApi.class, "https://api.bing.com");

        String xiaomi = bingApi.osjson("xiaomi", "en-us");
        System.out.println(xiaomi);
    }
}
