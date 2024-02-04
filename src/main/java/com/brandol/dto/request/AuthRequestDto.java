package com.brandol.dto.request;

import com.brandol.domain.enums.Gender;
import com.brandol.validation.annotation.ExistNickname;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AuthRequestDto {
    // 수정 필요
    @Getter
    public static class KakaoLoginRequest {
        private String email;
//        private String name;
    }

    @Getter
    @Setter
    public static class TermsAgreementReq {
        @NotNull
        private String email;
        @NotNull
        private List<Long> termsIdList;
    }

    @Getter
    @Setter
    public static class SetProfileDto{
        @NotNull
        private Long memberId;
        @NotBlank
        @ExistNickname
        private String nickname;
        @NotBlank
        private String name;
        private Gender gender;
        @NotNull
        private Integer age;
    }

}