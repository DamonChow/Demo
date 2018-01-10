package com.damon.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * 从zookeeper取properties配置
 *
 * @author Damon
 * @since 2017/12/25 15:09
 */
public class ZookeeperPropertiesConfigure extends PropertyPlaceholderConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperPropertiesConfigure.class);

    private String[] locationList;

    public void setLocationList(String[] locationList) {
        this.locationList = locationList;
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        String actualPlaceholder = placeholder;
        String defaultValue = null;
        int separatorIndex = placeholder.indexOf(valueSeparator);
        if (separatorIndex != -1) {
            actualPlaceholder = placeholder.substring(0, separatorIndex);
            defaultValue = placeholder.substring(separatorIndex + valueSeparator.length());
        }
        return PropertiesUtils.getString(actualPlaceholder, defaultValue);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) {
        PropertiesUtils.putProperties(props);
        super.processProperties(beanFactoryToProcess, props);
    }

    @Override
    protected Properties mergeProperties() throws IOException {
        logger.info("解析本地配置.");
        Properties localProperties = super.mergeProperties();
        logger.info("本地共配置{}条", localProperties.size());

        Properties zkProperties = loadZookeeperConfig();
        zkProperties.putAll(localProperties);
        return zkProperties;
    }

    private Properties loadZookeeperConfig() {
        Properties properties = new Properties();
        String zkHost = System.getProperty("zkHost");
        if (StringUtils.isEmpty(zkHost)) {
            throw new IllegalArgumentException("请配置vm中的[zkHost]属性.");
        }
        logger.info("开始读取zookeeper|{}|中的配置文件。", zkHost);
        if (ArrayUtils.isEmpty(locationList)) {
            throw new IllegalArgumentException("ZookeeperPropertiesConfigure必须配置locationList属性.");
        }

        try {
            CuratorFramework curatorFramework = ZooKeeperFactory.get(zkHost);
            for (String location : locationList) {
                logger.info("读取zookeeper中的配置文件：{}", location);
                byte[] data = ZookeeperConfig.getConfig(curatorFramework, location);
                String configValue = new String(data, "GBK");
                PropertiesUtils.handleProperties(configValue, properties);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("解析zookeeper中的属性出错。", e);
        }
        return properties;
    }

}