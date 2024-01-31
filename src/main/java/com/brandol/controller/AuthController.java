package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.config.security.TokenDto;
import com.brandol.dto.request.AuthResquestDto;
import com.brandol.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/kakao")
    public ApiResponse<TokenDto> login(@RequestBody AuthResquestDto.KakaoLoginRequest request){
        TokenDto tokenDto = authService.login(request);
        return ApiResponse.onSuccess(tokenDto);
        // return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), tokenDto);
    }

    // 회원가입 - 회원 정보 설정
    // 회원가입만 해도 로그인 된 상태일 때
    // 응답을 ApiResponse<AuthDto.signupResponse> 에서 TokenDto로 수정
    @PostMapping("/signup")
    public ApiResponse<TokenDto> signup(@RequestBody AuthResquestDto.SignupRequest request){
        return ApiResponse.onSuccess(authService.signup(request));
        /*TokenDto tokenDto = authService.signup(request);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), tokenDto);*/
    }


    /*@PostMapping("/signup/agreement")
    public ApiResponse<AuthDto.TermsAgreementRes> termsAgreement(@RequestBody AuthDto.TermsAgreementReq request){
        AuthDto.TermsAgreementRes termsAgreement = authService.termsAgreement(request);
        return ApiResponse.onSuccess(termsAgreement);
    }*/

    // 닉네임 중복 확인
    @PostMapping("/nickname/exist")
    public ApiResponse<Boolean> checkNickname(@RequestBody @Valid AuthResquestDto.NicknameCheckReq request) {
        Boolean isExist = authService.checkNicknameDuplicate(request);
        return ApiResponse.onSuccess(isExist);
    }
}
