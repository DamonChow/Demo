package com.damon.hessian.support;

import com.damon.hessian.common.HessianResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.NDC;
import org.springframework.util.StringUtils;

/**
 * Created by Damon on 2017/5/26.
 */
@Slf4j
public class HessianLogInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        String invocationDescription = getInvocationDescription(invocation);
        log.debug("接口调用开始：{}", invocationDescription);
        try {
            Object result = invocation.proceed();
            log.debug("接口调用成功：{}, 用时: {} 毫秒，结果: {}.", invocationDescription, (System.currentTimeMillis() - start), result);
            return result;
        } catch (Throwable ex) {
            log.error("接口调用失败：", ex);

            HessianResponse<Void> response = generateHessianResponse(ex);
            return response;
        }
    }

    private HessianResponse<Void> generateHessianResponse(Throwable ex) {
        HessianResponse<Void> response = new HessianResponse<Void>();
        if (ex instanceof Exception) {
            response.setTraceException((Exception) ex);
            response.setCode("1");
        }


        response.setTraceId(NDC.peek());
        response.setMessage(ex.getMessage());
        return response;
    }

    /**
     * Return a description for the given method invocation.
     *
     * @param invocation the invocation to describe
     * @return the description
     */
    private String getInvocationDescription(MethodInvocation invocation) {
        return "方法 '" + invocation.getMethod().getName() + "' , 类名 [" +
                invocation.getThis().getClass().getName() + "]" + "，参数 ["
                + StringUtils.arrayToCommaDelimitedString(invocation.getArguments()) + "]";
    }
}
