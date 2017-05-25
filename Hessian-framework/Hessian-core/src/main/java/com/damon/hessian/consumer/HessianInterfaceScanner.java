package com.damon.hessian.consumer;

import com.damon.hessian.annotation.HessianInterface;
import com.damon.hessian.common.ModuleEnum;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by Damon on 2017/5/24.
 */
public class HessianInterfaceScanner extends ClassPathBeanDefinitionScanner {

    public HessianInterfaceScanner(BeanDefinitionRegistry registry) {
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
        // if specified, use the given annotation and / or marker interface
        addIncludeFilter(new AnnotationTypeFilter(HessianInterface.class));

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
            for (BeanDefinitionHolder holder : beanDefinitions) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

                if (logger.isDebugEnabled()) {
                    logger.debug("Creating HessianFactoryBean with name '"
                            + holder.getBeanName() + "' and '"
                            + definition.getBeanClassName()
                            + "' HessianInterface");
                }

                AnnotationMetadata metadata = ((ScannedGenericBeanDefinition)definition).getMetadata();
                Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(HessianInterface.class.getName());
                ModuleEnum module = (ModuleEnum)annotationAttributes.get("module");
                String remote = (String)annotationAttributes.get("remote");
                String url = module.getContextUrl() + remote + "/" + holder.getBeanName();
                definition.getPropertyValues().add("serviceUrl", url);
                definition.getPropertyValues().add("serviceInterface", definition.getBeanClassName());
                definition.setBeanClass(HessianProxyFactoryBean.class);
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            }
        }
        return beanDefinitions;
    }

}