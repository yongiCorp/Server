package com.brandol.apiPayload.code.status;

import com.brandol.apiPayload.code.BaseCode;
import com.brandol.apiPayload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK,"COMMON200","성공입니다"),
    _CREATED(HttpStatus.CREATED,"COMMON201","생성 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason(){
        return com.brandol.apiPayload.code.ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus(){
        return com.brandol.apiPayload.code.ReasonDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(true)
                .message(message)
                .code(code)
                .build();
    }
}
