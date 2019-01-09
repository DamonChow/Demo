package com.damon.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import java.io.IOException;

/**
 * @author Damon
 * Created  on 2018/5/30.
 */
public class ObjectMapperUtil {

    private static class ObjectMapHolder {

        private static final ObjectMapper INSTANCE = new ObjectMapper();

        static {
            INSTANCE.setSerializationInclusion(Include.NON_NULL);
        }
    }


    public static ObjectMapper getObjectInstance() {
        return ObjectMapperUtil.ObjectMapHolder.INSTANCE;
    }

    /**
     * 将对象转换为json字符串
     */
    public static <T> String obj2string(T t) {
        try {
            return getObjectInstance().writeValueAsString(t);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }


    /**
     * 将字符串转为对象
     */
    public static <T> T str2obj(String jsonStr, Class<T> cls) {
        try {
            return getObjectInstance().readValue(jsonStr, cls);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }


}
