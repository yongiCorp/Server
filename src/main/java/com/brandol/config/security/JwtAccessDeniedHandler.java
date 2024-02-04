package com.brandol.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
인증(Authentification)에 실패했을 때 예외처리
* */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // 필요한 권한 없이(ex- ROLE_ADMIN) 접근하려 할 때 403 에러
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        System.out.println("JwtAccessDeniedHandler 실행" + accessDeniedException.getMessage());
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
