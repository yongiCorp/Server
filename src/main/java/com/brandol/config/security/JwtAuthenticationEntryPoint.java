package com.brandol.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 인가(Authorization)에 실패했을 때 예외처리
* */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 인증되지 않은 사용자(로그인 되지 않은)가 로그인 된 사용자가 필요한 요청에 접근했을 때 401 에러
        response.setCharacterEncoding("utf-8");
        System.out.println("JwtAuthenticationEntryPoint 실행" + authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 접근입니다.");
    }
}
