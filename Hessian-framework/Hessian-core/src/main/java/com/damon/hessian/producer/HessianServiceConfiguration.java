package com.damon.hessian.producer;

import com.damon.hessian.annotation.HessianInterface;
import com.damon.hessian.annotation.HessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Damon on 2017/5/24.
 */
@Configuration
public class HessianServiceConfiguration implements BeanFactoryPostProcessor {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Object> hessianServiceBeanMap = beanFactory.getBeansWithAnnotation(HessianService.class);
        logger.info("bean with annotation  : " + hessianServiceBeanMap);
        if (hessianServiceBeanMap == null || hessianServiceBeanMap.isEmpty()) {
            logger.warn("No hessian was found. Please check your configuration.");
            return;
        }

        for (Map.Entry<String, Object> entry : hessianServiceBeanMap.entrySet()) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HessianServiceExporter.class);
            String serviceName = entry.getKey();
            Object serviceBean = entry.getValue();
            builder.addPropertyReference("service", serviceName);
            Class<?> interfaceClass = serviceBean.getClass().getInterfaces()[0];
            String interfaceName = interfaceClass.getName();
            builder.addPropertyValue("serviceInterface", interfaceName);

            HessianInterface annotation = interfaceClass.getAnnotation(HessianInterface.class);
            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(annotation.remote()
                    + "/" + serviceName.replace("Impl",""), builder.getBeanDefinition());
            logger.debug("Creating HessianServiceExporter with ref beanName '" + serviceName + "' and '" + interfaceName  + "' serviceInterface");
        }
    }

}
