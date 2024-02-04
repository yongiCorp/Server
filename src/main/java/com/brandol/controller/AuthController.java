package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.config.security.TokenDto;
import com.brandol.dto.request.AuthRequestDto;
import com.brandol.dto.response.AuthResponseDto;
import com.brandol.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login/kakao")
    public ApiResponse<TokenDto> login(@RequestBody AuthRequestDto.KakaoLoginRequest request){
        TokenDto tokenDto = authService.login(request);
        return tokenDto == null ? ApiResponse.onFailure(ErrorStatus._NOT_EXIST_MEMBER.getCode(), ErrorStatus._NOT_EXIST_MEMBER.getMessage(), tokenDto) : ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), tokenDto);
    }

    @PostMapping("/signup")
    public ApiResponse<AuthResponseDto.SignUpDto> signUp(@RequestBody @Valid AuthRequestDto.SignUpDto request) {
        AuthResponseDto.SignUpDto signUpDto = authService.signUp(request);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), signUpDto);
    }

    // jwt access token 으로 memberId 가져오기 test
    @GetMapping("/test")
    public ApiResponse<String> test(Authentication authentication) {
        String memberId = authentication.getName();
        System.out.println("memberId = " + memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), "test");
    }

}