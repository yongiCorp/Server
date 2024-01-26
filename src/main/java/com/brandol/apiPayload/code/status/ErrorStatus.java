package com.brandol.apiPayload.code.status;

import com.brandol.apiPayload.code.BaseCode;
import com.brandol.apiPayload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),

    //브랜드
    _NOT_EXIST_BRAND(HttpStatus.BAD_REQUEST,"BRAND400","존재하지 않는 브랜드입니다."),

    //멤버 브랜드 리스트
    _ALREADY_EXIST_MEMBERM_BRAND_LIST(HttpStatus.BAD_REQUEST,"MEMBER-BRAND-LIST400","이미 구독중인 브랜드 입니다."),

    //메인 배너
    _CANNOT_LOAD_MAIN_BANNER(HttpStatus.NOT_FOUND,"MAIN-BANNER404","메인배너 조회에 실패했습니다."),
    _CANNOT_LOAD_BRANDOL_MAIN_BANNER(HttpStatus.NOT_FOUND,"MAIN-BANNER4004","시스템 기본값인 BRANDOL이 존재하지 않습니다."),

    //서브 배너
    _CANNOT_LOAD_SUB_BANNER(HttpStatus.NOT_FOUND,"SUB-BANNER404","서브배너 조회에 실패했습니다."),

    //콘텐츠
    _CANNOT_LOAD_CONTENTS(HttpStatus.NOT_FOUND,"CONTENTS404","콘텐츠 조회에 실패하였습니다."),

    //유저
    _NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST,"MEMBER400","존재하지 않는 회원입니다."),

    //DB
    _DUPLICATE_DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"DATABASE500","DB에서 중복 데이터가 조회됨"),

    //파일
    _FILE_NAME_ERROR(HttpStatus.BAD_REQUEST,"FILE400","잘못된 파일 형식명입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason(){
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus(){
        return ReasonDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .message(message)
                .code(code)
                .build();
    }
}
