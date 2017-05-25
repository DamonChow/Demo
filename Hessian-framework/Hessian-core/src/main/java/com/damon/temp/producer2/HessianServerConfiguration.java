package com.damon.temp.producer2;

import com.damon.hessian.common.AopTargetUtils;
import com.damon.hessian.annotation.HessianInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

import static org.springframework.util.Assert.notNull;


/**
 * Created by Damon on 2017/5/24.
 */
public class HessianServerConfiguration implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware, BeanNameAware{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String beanName;

    private String basePackage;

    private boolean includeAnnotationConfig = true;

    private ApplicationContext applicationContext;

    private BeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator() {
        @Override
        protected String buildDefaultBeanName(BeanDefinition definition) {
            AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(HessianInterface.class.getName());
            return (String) annotationAttributes.get("uri");
        }
    };

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    private Class<?>[] getActualInterfaces(Object obj) {
        try {
            return AopTargetUtils.getInterfaces(obj);
        } catch (Exception e) {
            logger.error(obj + " find Actual Interface error", e);
        }
        return new Class[0];
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        HessianServiceScanner scan = new HessianServiceScanner((BeanDefinitionRegistry)beanFactory);
        scan.setResourceLoader(this.applicationContext);
        scan.setBeanNameGenerator(this.nameGenerator);
        // 引入注解配置
        scan.setIncludeAnnotationConfig(this.includeAnnotationConfig);
        scan.registerFilters();

        scan.scan(basePackage);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

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