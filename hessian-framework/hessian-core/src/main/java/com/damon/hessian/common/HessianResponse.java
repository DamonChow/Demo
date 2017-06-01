package com.damon.hessian.common;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/26.
 */
public class HessianResponse<T> implements Serializable {

    private static final long serialVersionUID = -8121233496140382091L;

    private String code;

    private String message;

    private T data;

    private Exception traceException;

    private String traceId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Exception getTraceException() {
        return traceException;
    }

    public void setTraceException(Exception traceException) {
        this.traceException = traceException;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "HessianResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", traceException=" + traceException +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
