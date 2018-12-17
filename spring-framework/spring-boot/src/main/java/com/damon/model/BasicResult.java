package com.damon.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BasicResult<T> {


  @JsonView(BasicResultView.class)
  private int errorCode;
  //返回的用户展示的详细信息
  @JsonView(BasicResultView.class)
  private String errorMsg;

  //成功标识
  @JsonView(BasicResultView.class)
  private boolean success = false;

  //返回的具体类型
  @JsonView(BasicResultView.class)
  private T data;

  interface BasicResultView {

  }

  public static <T> BasicResult<T> fail(int code, String msg) {
    BasicResult basicResult = new BasicResult();
    basicResult.setErrorCode(code);
    basicResult.setErrorMsg(msg);
    return basicResult;
  }



  public static <T> BasicResult<T> success() {
    BasicResult basicResult = new BasicResult<>();
    basicResult.setSuccess(true);
    return basicResult;
  }

  public static <T> BasicResult<T> success(T t) {
    BasicResult<T> basicResult = success(t, BasicResultCode.SUCCESS.getMsg());
    basicResult.setSuccess(true);
    return basicResult;
  }


  public static <T> BasicResult<T> success(T t, String msg) {
    BasicResult<T> r = new BasicResult<>();
    r.setErrorCode(BasicResultCode.SUCCESS.getCode());
    r.setErrorMsg(msg);
    r.setData(t);
    r.setSuccess(true);
    return r;
  }
}
