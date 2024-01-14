package com.brandol.apiPayload.exception;

import com.brandol.apiPayload.code.BaseCode;
import com.brandol.apiPayload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{

    private final BaseCode code;

    public ReasonDto getErrorReason(){
        return this.code.getReason();
    }

    public ReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }

}