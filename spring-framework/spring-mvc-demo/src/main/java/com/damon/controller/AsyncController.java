package com.damon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/1/12 14:40
 */
@Controller
@RequestMapping("/async/")
public class AsyncController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @ResponseBody
    @RequestMapping("info")
    public String info() {
        log.info("test---------------------");
        return "info";
    }

    private Callable<String> getCallable() {
        return () -> {
            log.info("before sleep");
            Thread.sleep(2000);
            log.info("after sleep");
            return "SUCCESS";
        };
    }

    private Callable<String> getTimeOutCallable() {
        return () -> {
            String en = "EXCEPTION";
            log.info("执行超时 thread id is ：" + Thread.currentThread().getId());
            return en;
        };
    }

    @RequestMapping("/response-body")
    @ResponseBody
    public Callable<String> callable() {
        log.info("in response-body");
        Callable<String> result = getCallable();
        log.info("return {}", result);
        return result;
    }

    @RequestMapping("/exception")
    public @ResponseBody
    Callable<String> callableWithException(@RequestParam(required = false, defaultValue = "true") boolean handled) {
        log.info("in exception");
        return () -> {
            log.info("before exception");
            Thread.sleep(2000);
            log.info("after exception");
            if (handled) {
                // see handleException method further below
                throw new IllegalStateException("Callable error");
            } else {
                throw new IllegalArgumentException("Callable error");
            }
        };
    }

    @RequestMapping("/custom-timeout-handling")
    @ResponseBody
    public WebAsyncTask<String> callableWithCustomTimeoutHandling() {
        log.info("in custom-timeout-handling");
        Callable<String> result = getCallable();
        log.info("return {}", result);
        WebAsyncTask webAsyncTask = new WebAsyncTask(1000, result);
        webAsyncTask.onTimeout(getTimeOutCallable());
        return webAsyncTask;
    }

    @RequestMapping("/custom-timeout-handling2")
    @ResponseBody
    public WebAsyncTask<String> callableWithCustomTimeoutHandling2() {
        log.info("in custom-timeout-handling2");
        Callable<String> result = getCallable();
        log.info("return2 {}", result);
        WebAsyncTask webAsyncTask = new WebAsyncTask(3000, result);
        webAsyncTask.onTimeout(getTimeOutCallable());
        return webAsyncTask;
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(IllegalStateException ex) {
        log.error("出错，", ex);
        return "Handled exception: " + ex.getMessage();
    }

    @RequestMapping(value = "/longtimetask", method = RequestMethod.GET)
    @ResponseBody
    public WebAsyncTask<String> longTimeTask() {
        log.info("/longtimetask被调用 thread id is : " + Thread.currentThread().getId());
        Callable<String> callable = getCallable();
        log.info("/longtimetask被end thread id is : " + Thread.currentThread().getId());
        return new WebAsyncTask(callable);
    }
}
