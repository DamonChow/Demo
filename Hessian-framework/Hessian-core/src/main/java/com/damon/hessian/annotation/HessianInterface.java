package com.damon.hessian.annotation;

import com.damon.hessian.common.ModuleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Damon on 2017/5/24.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HessianInterface {

    ModuleEnum module(); // 客户端访问链接前半部分配置 如 http://localhost:8004/demo-app

    String remote() default "/remote"; // 客户端访问链接后半部分hessian配置 如 /remote
}
