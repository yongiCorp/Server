package com.brandol.config;

import com.brandol.config.security.JwtAccessDeniedHandler;
import com.brandol.config.security.JwtAuthenticationEntryPoint;
import com.brandol.config.security.JwtAuthenticationFilter;
import com.brandol.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfing {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /*public WebSecurityCustomizer webSecurityCustomizer() {
        // ACL(Access Control List, 접근 제어 목록)의 예외 URL 설정
        return (web)
                -> web
                .ignoring()
                //.antMatchers("/users/login/kakao");
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법 설정
        http
                // jwt 토큰 사용을 위한 설정
                .csrf().disable()// csrf 보안 토큰 disable
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제
                .formLogin().disable() // 기본 로그인 페이지 disable
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class) // filter 등록
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// session 사용하지 않음

                // 예외 처리
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/users/login/kakao","/swagger-ui/**").permitAll() // "/users/login/kakao" 엔드포인트에 대한 접근을 모든 사용자에게 허용
                .anyRequest().authenticated();// 나머지 요청은 모두 인증된 사용자만 접근 가능


                /*.authorizeRequests() // ServletRequest를 사용하는 요청에 대한 접근 제한 설정(인증 필요)
                .antMatchers("/api/mypage/**") // 마이페이지 인증 필요
                .authenticated() // 인증을 완료해야 접근을 허용
                .antMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 페이지
                //hasRole("권한"): 특정 레벨의 권한을 가진 사용자만 접근을 허용한다.(SecurityContext에 저장했던 Authentication 객체의 Authorities를 검사한다.)
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll() // 검증없이 이용가능 (POST 요청가능)
                .anyRequest().permitAll()// 인증 없이 접근 허용*/
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
