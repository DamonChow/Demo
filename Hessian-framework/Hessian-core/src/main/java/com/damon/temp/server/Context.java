package com.damon.temp.server;

/**
 * Created by Damon on 2017/5/24.
 */
public enum Context {
    API_V2("api.v2.remote.url");

    private String remoteUrlConfigKey;

    private Context(String remoteUrlConfigKey) {
        this.remoteUrlConfigKey = remoteUrlConfigKey;
    }

    public String getRemoteUrl() {
        return System.getProperty(remoteUrlConfigKey, "http://127.0.0.1/sys/remote");
    }
}
