package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.config.security.TokenDto;
import com.brandol.domain.Member;
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
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), tokenDto);
    }

    // 이용약관 동의
    @PostMapping("/signup/terms")
    public ApiResponse<AuthResponseDto.AgreeTermsDto> agreeTerms(@RequestBody AuthRequestDto.TermsAgreementReq request){
        AuthResponseDto.AgreeTermsDto agreeTermsDto = authService.agreeTerms(request);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), agreeTermsDto);
    }

    // jwt access token 으로 memberId 가져오기 test
    @GetMapping("/test")
    public ApiResponse<String> test(Authentication authentication) {
        String memberId = authentication.getName();
        System.out.println("memberId = " + memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), "test");
    }

    // 프로필 설정
    @PostMapping("signup/profile")
    public ApiResponse<Member> setProfile(@RequestBody @Valid AuthRequestDto.SetProfileDto request) {
        //AuthResponseDto.SetProfileDto setProfileDto = authService.setProfile(request);
        Member newMember = authService.setProfile(request);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), newMember);
    }

}