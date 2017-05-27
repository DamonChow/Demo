package com.damon.hessian.consumer;

import com.damon.hessian.support.TraceHessianProxyFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Damon on 2017/5/24.
 */
public class HessianClientScanner extends ClassPathBeanDefinitionScanner {

    private String context;

    private String readTimeout;

    public HessianClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping HessianFactoryBean with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName()
                    + "' HessianInterface"
                    + ". Bean already defined with the same name!");
            return false;
        }
    }

    public void registerFilters() {
        // default include filter that accepts all classes
        addIncludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });

        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String className = metadataReader.getClassMetadata().getClassName();
                return className.endsWith("package-info");
            }
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            logger.warn("No hessian was found in '"
                    + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            //项目中特殊处理
//            context = this.context.replace("/ServiceExporter","");

            for (BeanDefinitionHolder holder : beanDefinitions) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                String url = context + "/" + holder.getBeanName();
                definition.getPropertyValues().add("proxyFactory", new TraceHessianProxyFactory());
                definition.getPropertyValues().add("readTimeout", readTimeout);
                definition.getPropertyValues().add("overloadEnabled", Boolean.TRUE);
//                definition.getPropertyValues().add("connectTimeout", readTimeout);
                definition.getPropertyValues().add("serviceUrl", url);
                definition.getPropertyValues().add("serviceInterface", definition.getBeanClassName());
                definition.setBeanClass(HessianProxyFactoryBean.class);
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

                if (logger.isDebugEnabled()) {
                    logger.debug("Creating HessianFactoryBean with name '"
                            + holder.getBeanName() + "' and '"
                            + definition.getBeanClassName()
                            + "' HessianInterface");
                }
            }
        }
        return beanDefinitions;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        if (!StringUtils.isEmpty(context)) {
            this.context = context.replace("/ServiceExporter","");
        }
    }

    public String getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(String readTimeout) {
        this.readTimeout = readTimeout;
    }
}