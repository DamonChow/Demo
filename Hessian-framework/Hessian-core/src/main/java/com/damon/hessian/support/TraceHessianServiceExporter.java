package com.damon.hessian.support;

import org.apache.log4j.NDC;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Damon on 2017/5/26.
 */
public class TraceHessianServiceExporter extends HessianServiceExporter {
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            NDC.push(request.getHeader("txnID"));
            super.handleRequest(request, response);
        } finally {
            NDC.pop();
        }
    }
}
