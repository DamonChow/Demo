package com.damon.hessian.common;

import lombok.Data;
import org.apache.log4j.NDC;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/26.
 */
@Data
public class HessianResponse<T> implements Serializable {

    private static final long serialVersionUID = -8121233496140382091L;

    private String code;

    private String message;

    private T data;

    private Exception traceException;

    private String traceId;

    public HessianResponse() {
        setSuccess();
    }

    public HessianResponse(T data) {
        setSuccess();
        this.data = data;
    }

    private void setSuccess() {
        code = "0";
        message = "操作成功！";
        setTraceId(NDC.peek());
    }

}