package com.damon.hessian.support;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.log4j.NDC;

import java.net.URL;

/**
 * Created by Damon on 2017/5/26.
 */
public class TraceHessianProxy extends HessianProxy {

    public TraceHessianProxy(URL url, HessianProxyFactory factory, Class<?> api) {
        super(url, factory, api);
    }

    /**
     * Add txId to request headers
     * @param conn
     */
    @Override
    protected void addRequestHeaders(HessianConnection conn) {
        super.addRequestHeaders(conn);
        conn.addHeader("txnID", NDC.peek());
    }
}
