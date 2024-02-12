package com.brandol.dto.request;

import com.brandol.domain.enums.Gender;
import com.brandol.validation.annotation.ExistNickname;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AuthRequestDto {

    @Getter
    public static class KakaoLoginRequest {
        private String email;
        private String name;
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
    public static class SetProfileDto {

        @NotBlank
        @ExistNickname
        private String nickname;

        private Gender gender;
        @NotNull
        private Integer age;
    }

    @Getter
    @Setter
    public static class SignUpDto {
        @NotNull
        private String email;
        @NotNull
        private List<Long> termsIdList;
        @NotBlank
        @ExistNickname
        private String nickname;

        private Gender gender;
        @NotNull
        private Integer age;
    }

    @Getter
    public static class UpdateNicknameDto {
        private String nickname;
    }

}