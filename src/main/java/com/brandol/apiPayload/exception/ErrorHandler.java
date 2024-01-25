package com.brandol.apiPayload.exception;

import com.brandol.apiPayload.code.BaseCode;

public class ErrorHandler extends GeneralException{ //예외 처리 전담 핸들러
    public ErrorHandler(BaseCode baseCode){
        super(baseCode);
    }
}
