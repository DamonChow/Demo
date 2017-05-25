package com.damon.hessian.consumer;

import com.damon.hessian.common.ModuleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.util.Assert.notNull;


/**
 * Created by Damon on 2017/5/24.
 */
public class HessianClientConfiguration implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String beanName;

    private String basePackage;

    private Resource[] locations;

    private BeanNameGenerator nameGenerator;

    private boolean includeAnnotationConfig = true;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.locations, "Property 'locations' is required " + beanName);
        notNull(this.basePackage, "Property 'basePackage' is required " + beanName);
        Properties props = new Properties();
        loadProperties(props);

        for (ModuleEnum module : ModuleEnum.values()) {
            module.setContextUrl(props.getProperty(module.getContext()));
        }
    }

    protected void loadProperties(Properties props) throws IOException {
        if (this.locations != null) {
            for (Resource location : this.locations) {
                if (logger.isInfoEnabled()) {
                    logger.info("Loading properties file from " + location);
                }
                try {
                    PropertiesLoaderUtils.fillProperties(props, location);
                }catch (IOException ex) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Could not load properties from " + location + ": " + ex.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HessianInterfaceScanner scan = new HessianInterfaceScanner(registry);
        scan.setResourceLoader(this.applicationContext);
        scan.setBeanNameGenerator(this.nameGenerator);
        // 引入注解配置
        scan.setIncludeAnnotationConfig(this.includeAnnotationConfig);
        scan.registerFilters();

        scan.scan(basePackage);
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Resource[] getLocations() {
        return locations;
    }

    public void setLocations(Resource[] locations) {
        this.locations = locations;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public boolean isIncludeAnnotationConfig() {
        return includeAnnotationConfig;
    }

    public void setIncludeAnnotationConfig(boolean includeAnnotationConfig) {
        this.includeAnnotationConfig = includeAnnotationConfig;
    }
}