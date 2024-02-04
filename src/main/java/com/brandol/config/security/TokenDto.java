package com.brandol.config.security;

import lombok.*;

import java.util.Date;

/*@Data
@AllArgsConstructor
public class TokenDto {
    //1. 로그인 시 토큰을 응답
    private String types;  // atk, rtk
    private String token; // jwt 토큰
    private Date tokenExpriresTime; // 토큰 만료시간
}*/

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}