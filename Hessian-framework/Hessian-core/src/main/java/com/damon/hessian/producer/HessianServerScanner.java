package com.damon.hessian.producer;

import com.damon.hessian.support.HessianLogInterceptor;
import com.damon.hessian.support.TraceHessianServiceExporter;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Damon on 2017/5/24.
 */
public class HessianServerScanner extends ClassPathBeanDefinitionScanner {

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    public HessianServerScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /*@Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (!beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
    }*/

    /**
     * {@inheritDoc}
     */
    /*@Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping HessianServiceExporter with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName()
                    + "' serviceInterface "
                    + ". Bean already defined with the same name!");
            return false;
        }
    }*/

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
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(candidate);
                candidate.setScope(scopeMetadata.getScopeName());
                String beanName = beanNameGenerator.generateBeanName(candidate, getRegistry());
                if (candidate instanceof AbstractBeanDefinition) {
                    postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
                }

                ScannedGenericBeanDefinition bd = (ScannedGenericBeanDefinition) candidate;
                bd.setBeanClass(TraceHessianServiceExporter.class);
                bd.setBeanClassName(TraceHessianServiceExporter.class.getName());
                bd.getPropertyValues().add("service", new RuntimeBeanReference(beanName));
                String interfaceName = getInterfaceName(bd);
                bd.getPropertyValues().add("serviceInterface", interfaceName);
                bd.getPropertyValues().add("interceptors", new Object[]{new HessianLogInterceptor()});

                String hessianBeanName = "/ServiceExporter/" + beanName.replace("Impl", "");
                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, hessianBeanName);
                definitionHolder = applyScopedProxyMode(scopeMetadata, definitionHolder, getRegistry());
                beanDefinitions.add(definitionHolder);
                registerBeanDefinition(definitionHolder, getRegistry());
                if (logger.isDebugEnabled()) {
                    logger.debug("Creating HessianServiceExporter with name '"
                            + hessianBeanName + "' and '"
                            + interfaceName
                            + "' serviceInterface");
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

    private String getInterfaceName(ScannedGenericBeanDefinition bd) {
        String[] interfaces = bd.getMetadata().getInterfaceNames();
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }

        return interfaces[0];
    }

}