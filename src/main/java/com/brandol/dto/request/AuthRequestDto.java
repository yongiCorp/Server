package com.brandol.dto.request;

import com.brandol.domain.enums.Gender;
import com.brandol.validation.annotation.ExistNickname;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Setter
    public static class UpdateNicknameDto {
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 1, max = 10, message = "닉네임은 1자 이상 10자 이하로 입력해주세요.")
        @ExistNickname
        private String nickname;
    }

}