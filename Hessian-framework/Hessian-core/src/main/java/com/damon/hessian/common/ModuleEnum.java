package com.damon.hessian.common;

/**
 * Created by Damon on 2017/5/24.
 */
public enum ModuleEnum {

    MEMBER_APP("member-app"),

    USER_APP("user-app"),

    DEMO_APP("demo-app");

    private String context;

    private String contextUrl;

    ModuleEnum(String context) {
        this.context = context;
    }

    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    public String getContextUrl(){
        return contextUrl;
    }

    public String getContext() {
        return context;
    }
}
