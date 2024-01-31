package com.brandol.config.security;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //log.info("JwtAuthenticationFilter 실행");
        System.out.println("JwtAuthenticationFilter 실행");
        // Request Header에서 accessToken 추출
        String accessToken = jwtProvider.resolveToken(request);

        System.out.println("resolveToken함수 실행: " + accessToken);

        // ValidateToken()으로 유효성 검사
        // if (jwt != null && jwtTokenProvider.validateToken(jwt, JwtTokenProvider.TokenType.ACCESS)) : refresh 토큰 아직 추가 안함
        if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {

            System.out.println("validateAccessToken함수 실행: " + accessToken);
            // 토큰 유효하면(검증되면) 토큰으로부터 Authentication 인증객체 가져와서 SecurityContext에 저장
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }/* else {
            SecurityContextHolder.clearContext();
            System.out.println("403 에러 발생");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다.");
            //SecurityContextHolder.getContext().setAuthentication(null);
            //throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }*/
        filterChain.doFilter(request, response);
    }
}
