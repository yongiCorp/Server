package com.brandol.config;

import com.brandol.config.security.JwtAccessDeniedHandler;
import com.brandol.config.security.JwtAuthenticationEntryPoint;
import com.brandol.config.security.JwtAuthenticationFilter;
import com.brandol.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/health",
                "/"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법 설정
        http
                // jwt 토큰 사용을 위한 설정
                .csrf().disable()// csrf 보안 토큰 disable
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제
                .formLogin().disable() // 기본 로그인 페이지 disable
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class) // filter 등록(=> JwtAuthenticationFilter 따로 Bean으로 등록안해도 됨)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// session 사용하지 않음

                // 예외 처리
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                //.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll() // swagger 허용
                .antMatchers("/auth/login/kakao", "/auth/signup").permitAll()// url 접근을 모든 사용자에게 허용
                .anyRequest().authenticated();// 나머지 요청은 모두 인증된 사용자만 접근 가능

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}