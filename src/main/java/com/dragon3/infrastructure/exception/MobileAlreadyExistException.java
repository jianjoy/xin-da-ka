package com.dragon3.infrastructure.exception;


import com.dragon3.infrastructure.model.RestResponse;

public class MobileAlreadyExistException extends RestfulException {

	@Override
	public RestResponse.HttpCode getHttpCode() {
		return RestResponse.HttpCode.MOBILE_ALREADY_EXIST;
	}
}
