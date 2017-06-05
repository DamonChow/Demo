package com.damon.hessian.producer;

import com.damon.hessian.common.HessianAnnotationBeanNameGenerator;
import com.damon.hessian.support.HessianLogInterceptor;
import com.damon.hessian.support.TraceHessianServiceExporter;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;

import java.beans.Introspector;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 通过扫描接口暴露服务
 *
 * Created by Damon on 2017/5/24.
 */
public class HessianServerScannerByInterface extends ClassPathBeanDefinitionScanner {

    private BeanNameGenerator beanNameGenerator = new HessianAnnotationBeanNameGenerator();

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    public HessianServerScannerByInterface(BeanDefinitionRegistry registry) {
        super(registry, false);
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
   protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
       return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
   }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
        HessianLogInterceptor hessianLogInterceptor = generateHessianLogInterceptor();

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(candidate);
                candidate.setScope(scopeMetadata.getScopeName());
                String beanName = beanNameGenerator.generateBeanName(candidate, getRegistry());
                if (candidate instanceof AbstractBeanDefinition) {
                    postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
                }
                if (candidate instanceof AnnotatedBeanDefinition) {
                    AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
                }

                ScannedGenericBeanDefinition bd = (ScannedGenericBeanDefinition) candidate;
                String interfaceName = bd.getBeanClassName();
                Class<?> clazz = null;
                try {
                    clazz = bd.resolveBeanClass(getResourceLoader().getClassLoader());
                } catch (ClassNotFoundException e) {
                    logger.error("error", e);
                }
                bd.getPropertyValues().add("service", ((ApplicationContext)getResourceLoader()).getBean(clazz));
                bd.getPropertyValues().add("serviceInterface", interfaceName);
                bd.getPropertyValues().add("interceptors", new Object[]{hessianLogInterceptor});
                bd.setBeanClass(TraceHessianServiceExporter.class);
                bd.setBeanClassName(TraceHessianServiceExporter.class.getName());

                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
                definitionHolder = applyScopedProxyMode(scopeMetadata, definitionHolder, getRegistry());
                beanDefinitions.add(definitionHolder);
                registerBeanDefinition(definitionHolder, getRegistry());
                if (logger.isDebugEnabled()) {
                    logger.debug("Creating TraceHessianServiceExporter with name '"
                            + beanName + "' and '"
                            + interfaceName + "' serviceInterface");
                }
            }
        }

        if (beanDefinitions.isEmpty()) {
            logger.warn("No hessian was found in '"
                    + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        }
        return beanDefinitions;
    }

    private BeanDefinitionHolder applyScopedProxyMode(ScopeMetadata metadata, BeanDefinitionHolder definition,
                                                      BeanDefinitionRegistry registry) {
        ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
        if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
            return definition;
        }
        boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
        return ScopedProxyUtils.createScopedProxy(definition, registry, proxyTargetClass);
    }

    private HessianLogInterceptor generateHessianLogInterceptor() {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HessianLogInterceptor.class);
        getRegistry().registerBeanDefinition(Introspector.decapitalize(HessianLogInterceptor.class.getSimpleName()), builder.getBeanDefinition());
        return ((ApplicationContext)getResourceLoader()).getBean(HessianLogInterceptor.class);
    }

}