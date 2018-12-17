package com.damon.config;

import com.damon.model.BasicResult;
import com.damon.model.BasicResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


@Configuration
@Slf4j
@RestControllerAdvice
@ConditionalOnWebApplication
public class ExceptionConfiguration {

    @ConditionalOnClass(MethodArgumentNotValidException.class)
    @Configuration
    @RestControllerAdvice
    static class MethodArgumentNotValidExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.OK)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        //验证requestbody失败异常的处理
        public BasicResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            logError("methodArgumentNotValidException", e);
            return BasicResult.fail(BasicResultCode.PARAM_MISS_ERROR.getCode(),
                    MethodArgumentNotValidExceptionHelper.firstErrorMessage(e.getBindingResult()));
        }


        private static class MethodArgumentNotValidExceptionHelper {

            static String firstErrorMessage(BindingResult bindingResult) {
                return bindingResult.getAllErrors().stream().findFirst()
                        .map(ObjectError::getDefaultMessage).orElse("");
            }
        }
    }


    @ConditionalOnClass(org.springframework.validation.BindException.class)
    @Configuration
    @RestControllerAdvice
    static class BindExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.OK)
        @ExceptionHandler(BindException.class)
        public BasicResult handBindException(BindException e) {
            log.error("BindException", e.getMessage(), e);
            return BasicResult.fail(BasicResultCode.PARAM_VALIDATE_ERROR.getCode(),
                    BindExceptionHelper.firstErrorMessage(e.getAllErrors())
            );

        }

        static class BindExceptionHelper {

            static String firstErrorMessage(List<ObjectError> lists) {
                return lists.stream().findFirst()
                        .map(bind -> bind.getDefaultMessage()).orElse("");
            }
        }
    }

    @ConditionalOnClass(ConstraintViolationException.class)
    @Configuration
    @RestControllerAdvice
    static class ConstraintViolationExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.OK)
        @ExceptionHandler(ConstraintViolationException.class)
        //对于接口参数requestParam的validate验证
        public BasicResult handleConstraintViolationException(ConstraintViolationException e) {
            logError("constraintViolationException", e);
            return BasicResult.fail(BasicResultCode.PARAM_VALIDATE_ERROR.getCode(),
                    ConstraintViolationExceptionHelper.firstErrorMessage(e.getConstraintViolations()));
        }

        static class ConstraintViolationExceptionHelper {

            static String firstErrorMessage(Set<ConstraintViolation<?>> constraintViolations) {
                return constraintViolations.stream().findFirst()
                        .map(constraintViolation -> constraintViolation.getPropertyPath().toString() + ":"
                                + constraintViolation.getMessage()).orElse("");
            }
        }
    }

    @ConditionalOnClass(MissingServletRequestParameterException.class)
    @Configuration
    @RestControllerAdvice
    //对于接口需要参数，但是没有传参数的异常处理逻辑
    static class MissingServletRequestParameterExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.OK)
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public BasicResult handleMissingServletRequestParameterException(
                MissingServletRequestParameterException e) {
            logError("missingServletRequestParameterException", e);
            return BasicResult
                    .fail(BasicResultCode.PARAM_MISS_ERROR.getCode(),
                            String.format("参数%s未传", e.getParameterName()));
        }
    }

    @ConditionalOnClass(HttpMediaTypeNotSupportedException.class)
    @Configuration
    @RestControllerAdvice
    //对于传入的媒体类型不正确的异常处理
    static class HttpMediaTypeNotSupportedExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public BasicResult handleHttpMediaTypeNotSupportedException(
                HttpMediaTypeNotSupportedException e) {
            logError("httpMediaTypeNotSupportedException", e);
            return BasicResult
                    .fail(BasicResultCode.HTTP_UNSUPPORTED_MEDIA_TYPE_ERROR.getCode(),
                            BasicResultCode.HTTP_UNSUPPORTED_MEDIA_TYPE_ERROR.getMsg());
        }
    }


    @ConditionalOnClass(HttpRequestMethodNotSupportedException.class)
    @Configuration
    @RestControllerAdvice
    static class HttpRequestMethodNotSupportedExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        //对于接口方法不匹配的异常处理
        public BasicResult handleHttpRequestMethodNotSupportedException(
                HttpRequestMethodNotSupportedException e) {
            logError("httpRequestMethodNotSupportedException", e);
            return BasicResult
                    .fail(BasicResultCode.HTTP_METHOD_NOT_ALLOW_ERROR.getCode(),
                            BasicResultCode.HTTP_METHOD_NOT_ALLOW_ERROR.getMsg());
        }
    }


    @ConditionalOnClass(SQLException.class)
    @Configuration
    @RestControllerAdvice
    static class SqlExceptionConfiguration {

        @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
        @ExceptionHandler(SQLException.class)
        public BasicResult handleSqlException(SQLException e) {
            logError("SQLException", e);
            return BasicResult
                    .fail(BasicResultCode.SQL_ERROR.getCode(), BasicResultCode.SQL_ERROR.getMsg());
        }
    }


    @ExceptionHandler(RuntimeException.class)
    //对于接口运行时错误异常的处理
    public BasicResult handleRuntimeException(RuntimeException e) {
        logError("runtimeException", e);
        return BasicResult
                .fail(BasicResultCode.INNER_ERROR.getCode(), BasicResultCode.INNER_ERROR.getMsg());
    }

    @ExceptionHandler(Throwable.class)
    //对于所有异常的处理
    public BasicResult handleThrowable(Throwable t) {
        logError("throwableError", t);
        return BasicResult
                .fail(BasicResultCode.INNER_ERROR.getCode(), BasicResultCode.INNER_ERROR.getMsg());
    }

    static void logError(String name, Throwable t) {
        log.error("error name is {}", name, t);
    }
}
