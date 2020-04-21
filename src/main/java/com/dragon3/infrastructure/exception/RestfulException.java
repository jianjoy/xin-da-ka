package com.dragon3.infrastructure.exception;

import com.dragon3.infrastructure.model.RestResponse;

public abstract class RestfulException extends Exception{
    public RestfulException(String msg, Throwable e) {
        super(msg, e);
    }

    public RestfulException(String msg) {
        super(msg);
    }

    public RestfulException(){}

    public abstract RestResponse.HttpCode getHttpCode();

    @Override
    public String getMessage(){
        return getHttpCode().getMessage();
    }
}
