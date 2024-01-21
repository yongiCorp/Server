package com.brandol.apiPayload;

import com.brandol.apiPayload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess","code","message","result"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 2024-01-19 수정 "onSuccess" 가 "of" 기능을 흡수 함에 따라 파라미터 수정
    public static <T> ApiResponse<T> onSuccess(String code,String message,T data){
        return new ApiResponse<>(true, code,message,data);
    }


    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false,code,message,data);
    }
}
