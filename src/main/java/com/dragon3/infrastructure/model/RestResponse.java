package com.dragon3.infrastructure.model;

import com.dragon3.infrastructure.exception.RestfulException;
import com.dragon3.infrastructure.util.MapUtil;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

/**
 * restful接口返回对象
 */
@Getter @Setter
public class RestResponse {
    /**http状态码*/
    private HttpCode code;
    /**需要返回的对象*/
    private Object data;
    /**提示信息*/
    private String message;

    public enum HttpCode {
        /**成功*/
        OK(1, "成功"),
        UNAUTHORIZED(100, "验证失败/token过期"),
//        TOKEN_OVERDUE(101, "token过期"),
        PERMISSION_DENIED(102, "权限不足"),
//        PARENT_DISABLED(103, "用户已被禁用"),
        OPERATION_TOO_FAST(104, "操作过于频繁"),
        PARAMETER_ERROR(105, "参数错误"),
        VERIFY_CODE_ERROR(111, "验证码错误"),
        VERIFY_CODE_OVERTIME(113, "验证码超时"),
        VERIFY_CODE_USE_TOO_MANY(112, "验证码使用次数过多"),
        MOBILE_ALREADY_EXIST(121, "手机号已存在"),
        MOBILE_NOT_EXIST(122, "手机号不存在"),
        SAME_MOBILE(123, "不能与之前手机号相同"),
        SMS_SEND_ERROR(201, "短信发送错误"),
        SMS_SEND_TOO_FAST(202, "短信发送频率过快"),
        WECHAT_ERROR(211, "调用微信错误"),
        SYSTEM_ERROR(500, "系统错误"),
        ;
        private int code;
        private String message;
        HttpCode(int code, String message) {
            this.code = code;
            this.message = message;
        }
        @JsonValue
        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString(){
            return code+"";
        }
    }

    public RestResponse(){
        this(HttpCode.OK, "");
    }

    public RestResponse(Object data, HttpCode code, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public RestResponse(Object data) {
        this(data, HttpCode.OK, "");
    }

    public RestResponse(HttpCode code , String message){
        this(null, code, message);
    }

    public static RestResponse ok(){
        return new RestResponse();
    }

    public static RestResponse error(HttpCode code, String message){
        return new RestResponse(code, message);
    }
    public static RestResponse error(HttpCode code){
        return new RestResponse(code, code.getMessage());
    }
    public static RestResponse error(RestfulException ex){
        return RestResponse.error(ex.getHttpCode());
    }
    public static RestResponse data(Object data){
        return new RestResponse(data);
    }
    public static RestResponse data(Object... params){
        return new RestResponse(MapUtil.paramsToMap(params));
    }

}
