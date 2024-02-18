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
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증되지 않은 사용자입니다."),

    // Jwt 관련
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT400", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT401", "만료된 토큰입니다."),
    //브랜드
    _NOT_EXIST_BRAND(HttpStatus.BAD_REQUEST,"BRAND400","존재하지 않는 브랜드입니다."),

    //멤버 브랜드 리스트
    _ALREADY_EXIST_MEMBER_BRAND_LIST(HttpStatus.BAD_REQUEST,"MEMBER-BRAND-LIST400","이미 구독중인 브랜드 입니다."),
    _NOT_EXIST_MEMBER_BRAND_LIST(HttpStatus.BAD_REQUEST,"MEMBER-BRAND-LIST404","멤버-브랜드-리스트 조회 실패"),

    //메인 배너
    _CANNOT_LOAD_MAIN_BANNER(HttpStatus.NOT_FOUND,"MAIN-BANNER404","메인배너 조회에 실패했습니다."),
    _CANNOT_LOAD_BRANDOL_MAIN_BANNER(HttpStatus.NOT_FOUND,"MAIN-BANNER4004","시스템 기본값인 BRANDOL이 존재하지 않습니다."),

    //서브 배너
    _CANNOT_LOAD_SUB_BANNER(HttpStatus.NOT_FOUND,"SUB-BANNER404","서브배너 조회에 실패했습니다."),

    //팬덤
    _CANNOT_LOAD_FANDOM(HttpStatus.NOT_FOUND,"FANDOM404","팬덤 조회에 실패하였습니다."),
    _CANNOT_LOAD_FANDOM_LIKES(HttpStatus.NOT_FOUND,"FANDOM-LIKES404","Fandom-Like 조회에 실패하였습니다."),
    _CANNOT_LOAD_FANDOM_COMMENT(HttpStatus.NOT_FOUND,"FANDOM-COMMENT404","팬덤 댓글 조회에 실패하였습니다."),
    _CANNOT_LOAD_FANDOM_COMMENT_LIKES(HttpStatus.NOT_FOUND,"FANDOM_COMMENT-LIKES404","Fandom-COMMENT-Like 조회에 실패하였습니다."),

    //콘텐츠
    _CANNOT_LOAD_CONTENTS(HttpStatus.NOT_FOUND,"CONTENTS404","콘텐츠 조회에 실패하였습니다."),
    _CANNOT_LOAD_CONTENTS_LIKES(HttpStatus.NOT_FOUND,"CONTENT-LIKES404","Contents-Like 조회에 실패하였습니다."),
    _CANNOT_LOAD_CONTENTS_COMMENT(HttpStatus.NOT_FOUND,"CONTENTS-COMMENT404","콘텐츠 댓글 조회에 실패하였습니다."),
    _CANNOT_LOAD_CONTENTS_COMMENT_LIKES(HttpStatus.NOT_FOUND,"CONTENTS_COMMENT-LIKES404","Contents-COMMENT-Like 조회에 실패하였습니다."),
    // 커뮤니티
    _CANNOT_LOAD_COMMUNITY(HttpStatus.NOT_FOUND,"COMMUNITY404","커뮤니티 조회에 실패하였습니다."),
    _CANNOT_LOAD_COMMUNITY_LIKES(HttpStatus.NOT_FOUND,"COMMUNITY-LIKES404","Community-Like 조회에 실패하였습니다."),
    _CANNOT_LOAD_COMMUNITY_COMMENT(HttpStatus.NOT_FOUND,"COMMUNITY-COMMENT404","커뮤니티 댓글 조회에 실패하였습니다."),
    _CANNOT_LOAD_COMMUNITY_COMMENT_LIKES(HttpStatus.NOT_FOUND,"COMMUNITY_COMMENT-LIKES404","Community-COMMENT-Like 조회에 실패하였습니다."),
    // 댓글
    _NOT_EXIST_COMMENT(HttpStatus.NOT_FOUND,"COMMENT404","존재하지 않는 댓글입니다."),
    //유저
    _NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST,"MEMBER400","존재하지 않는 회원입니다."),
    _MEMBER_SIGNUP_REQUIRED(HttpStatus.NOT_FOUND, "MEMBER401", "회원가입을 진행해주세요."),
    _MEMBER_NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "MEMBER402", "중복된 닉네임입니다."),
    _MEMBER_NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "MEMBER403", "포인트가 부족합니다."),
    _INACTIVE_MEMBER(HttpStatus.NOT_FOUND, "MEMBER404", "비활성화된 회원입니다."),

    //채팅
    _IS_ALREADY_EXIST_CHAT_ROOM(HttpStatus.BAD_REQUEST,"CHAT400","이미 채팅방이 존재합니다."),
    _NOT_A_MEMBER_OF_THIS_ROOM(HttpStatus.BAD_REQUEST,"CHAT401","해당 채팅방 접근 권한이 없습니다."),

    //미션
    _NOT_EXIST_MISSION(HttpStatus.NOT_FOUND, "MISSION400", "존재하지 않는 미션입니다."),
    _NOT_CHALLENGING_MISSION(HttpStatus.NOT_FOUND, "MISSION401", "도전 중인 미션이 아닙니다."),
    _ALREADY_COMPLETED_MISSION(HttpStatus.BAD_REQUEST, "MISSION402", "이미 도전 완료된 미션입니다"),
    _EMPTY_SURVEY(HttpStatus.BAD_REQUEST, "MISSION403", "비어있는 항목이 존재합니다"),
    _NOT_COMPLETED_MISSION(HttpStatus.BAD_REQUEST, "MISSION404", "미션을 완료해 주세요"),

    //DB
    _DUPLICATE_DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"DATABASE500","DB에서 중복 데이터가 조회됨"),

    //파일
    _FILE_NAME_ERROR(HttpStatus.BAD_REQUEST,"FILE400","잘못된 파일 형식명입니다."),
    _FILE_AVATAR_INVALID(HttpStatus.BAD_REQUEST,"FILE401","저장할 아바타 파일이 없습니다."),

    // 아이템
    _NOT_EXIST_MY_ITEM(HttpStatus.NOT_FOUND,"MYITEM400","내 아이템이 존재하지 않습니다."),
    _NOT_EXIST_ITEM(HttpStatus.NOT_FOUND,"ITEM400","아이템이 존재하지 않습니다"),

    // 이용약관
    _TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "TERMS400", "이용약관이 존재하지 않습니다." ),
    _MANDATORY_AGREEMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "TERMS401", "필수 이용약관에 동의하지 않았습니다." );

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