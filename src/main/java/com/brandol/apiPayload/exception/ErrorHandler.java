package com.brandol.apiPayload.exception;

import com.brandol.apiPayload.code.BaseCode;

public class ErrorHandler extends GeneralException{
    public ErrorHandler(BaseCode baseCode){
        super(baseCode);
    }
}
