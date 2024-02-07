package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.config.security.TokenDto;
import com.brandol.dto.request.AuthRequestDto;
import com.brandol.dto.response.AuthResponseDto;
import com.brandol.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "로그인 관련 API", description = "로그인, 회원가입")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login/kakao")
    @Operation(summary = "로그인 API", description = "body에 사용자 이름, 이메일 넣어서 요청")
    public ApiResponse<TokenDto> login(@RequestBody AuthRequestDto.KakaoLoginRequest request){
        TokenDto tokenDto = authService.login(request);
        return tokenDto == null ? ApiResponse.onFailure(ErrorStatus._MEMBER_SIGNUP_REQUIRED.getCode(), ErrorStatus._MEMBER_SIGNUP_REQUIRED.getMessage(), tokenDto) : ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), tokenDto);
    }

    @Operation(summary = "회원가입 API", description = "body에 약관 동의 항목 Id, 이메일, 닉네임, 성별, 나이 넣어서 요청")
    @PostMapping("/signup")
    public ApiResponse<AuthResponseDto.SignUpDto> signUp(@RequestBody @Valid AuthRequestDto.SignUpDto request) {
        AuthResponseDto.SignUpDto signUpDto = authService.signUp(request);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), signUpDto);
    }

    // jwt access token 으로 memberId 가져오기 test
    @Operation(hidden = true)
    @GetMapping("/test")
    public ApiResponse<String> test(Authentication authentication) {
        String memberId = authentication.getName();
        System.out.println("memberId = " + memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), "test");
    }

}