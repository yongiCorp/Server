package com.brandol.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AuthResquestDto {
    // 수정 필요
    @Getter
    public static class KakaoLoginRequest { 
        private String email;
        private String name;
        private String gender;
    }


    @Getter
    public static class SignupRequest {
        @NotBlank
        private String email;
        @NotBlank
        private String name;
        @NotBlank
        private String nickname;
        @NotBlank
        private String gender;
        @NotNull
        private Integer age;
        //private UserStatus userStatus;
    }


    @Getter
    @Setter
    public static class NicknameCheckReq {
        @NotBlank
        private String nickname;
    }

    /* @Getter
    public static class TermsAgreementReq {
        @NotNull
        private Long termId;
        @NotNull
        private Long memberId;
    }*/

}
