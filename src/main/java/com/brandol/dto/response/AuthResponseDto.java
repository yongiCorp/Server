package com.brandol.dto.response;

import lombok.*;

public class AuthResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AgreeTermsDto {
        private Long memberId;
        private boolean signUp;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpDto {
        private Long memberId;
        private boolean signUp;
    }

}
