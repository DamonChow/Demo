package com.damon.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/12/25 15:09
 */
public class PropertiesUtils {

    private static Map<String, Object> properties = new ConcurrentHashMap<>();

    protected static void putProperties(Properties props) {
        for (Map.Entry entry : props.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            properties.put(key, value);
        }
    }

    protected static void modifyProperties(String configValue) {
        Properties props = new Properties();
        handleProperties(configValue, props);
        for (Map.Entry entry : props.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            properties.put(key, value);
        }
    }

    public static void handleProperties(String configValue, Properties properties) throws IllegalArgumentException {
        configValue = StringUtils.replace(configValue, "\r", "");
        String[] lines = StringUtils.split(configValue, "\n");
        for (String config : lines) {
            if (StringUtils.isBlank(config) || StringUtils.startsWith(config.trim(), "#")) {
                continue;
            }

            int index = StringUtils.indexOf(config, "=");
            if (index <= 0) {
                throw new IllegalArgumentException("错误配置:" + config);
            }
            String key = StringUtils.substring(config, 0, index);
            String value = StringUtils.substring(config, index + 1, config.length());
            if (StringUtils.isEmpty(value)) {
                throw new IllegalArgumentException("错误配置:" + config);
            }
            properties.put(key, value);
        }
    }

    private static Object getProperties(String key) {
        return properties.get(key);
    }

    public static String getString(String key) {
        Object value = getProperties(key);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public static String getString(String key, String defaultValue) {
        String value = getString(key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        }
        return defaultValue;
    }

    public static Integer getInteger(String key) {
        String value = getString(key);
        if (Objects.isNull(value)) {
            return null;
        }
        if (!StringUtils.isNumeric(value)) {
           throw new IllegalArgumentException("非数字配置");
        }
        return Integer.parseInt(value);
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        Integer value = getInteger(key);
        if (Objects.nonNull(value)) {
            return value;
        }
        return defaultValue;
    }

    public static Float getFloat(String key) {
        String value = getString(key);
        if (Objects.isNull(value)) {
            return null;
        }
        if (!StringUtils.isNumeric(value)) {
           throw new IllegalArgumentException("非数字配置");
        }
        return Float.parseFloat(value);
    }

    public static Float getFloat(String key, Float defaultValue) {
        Float value = getFloat(key);
        if (Objects.nonNull(value)) {
            return value;
        }
        return defaultValue;
    }

    public static Double getDouble(String key) {
        String value = getString(key);
        if (Objects.isNull(value)) {
            return null;
        }
        if (!StringUtils.isNumeric(value)) {
           throw new IllegalArgumentException("非数字配置");
        }
        return Double.parseDouble(value);
    }

    public static Double getDouble(String key, Double defaultValue) {
        Double value = getDouble(key);
        if (Objects.nonNull(value)) {
            return value;
        }
        return defaultValue;
    }

    public static Boolean getBoolean(String key) {
        String value = getString(key);
        if (Objects.isNull(value)) {
            return null;
        }
        return Boolean.valueOf(value);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        Boolean value = getBoolean(key);
        if (Objects.nonNull(value)) {
            return value;
        }
        return defaultValue;
    }

}