package com.damon.hessian.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import static org.springframework.util.Assert.notNull;


/**
 * Created by Damon on 2017/5/24.
 */
public class HessianServerConfiguration implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware, BeanNameAware, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String beanName;

    private String basePackage;

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
        notNull(this.basePackage, "Property 'basePackage' is required " + beanName);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //nothing...   BeanFactoryPostProcessor是在spring容器加载了bean的定义文件之后，在bean实例化之前执行的。往往在此处可以对bean的属性进行修改
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HessianServerScanner scan = new HessianServerScanner(registry);
        scan.setResourceLoader(this.applicationContext);
        // 引入注解配置
        scan.setIncludeAnnotationConfig(this.includeAnnotationConfig);
        scan.registerFilters();

        scan.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
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

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public boolean isIncludeAnnotationConfig() {
        return includeAnnotationConfig;
    }

    public void setIncludeAnnotationConfig(boolean includeAnnotationConfig) {
        this.includeAnnotationConfig = includeAnnotationConfig;
    }
}