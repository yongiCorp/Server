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

    // 회원 탈퇴
    @Operation(summary = "회원 탈퇴 API", description = "현재 로그인한 사용자 탈퇴 처리")
    @PatchMapping("/status")
    public ApiResponse<String> inactivateMember(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        String inactivateMember = authService.inactivateMember(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), inactivateMember);
    }

    @Operation(summary = "닉네임 수정", description = "현재 로그인한 사용자의 닉네임 수정, 닉네임은 10자 이하")
    @PatchMapping("/nickname")
    public ApiResponse<AuthResponseDto.UpdateNickname> updateNickname(Authentication authentication, @RequestBody @Valid AuthRequestDto.UpdateNicknameDto updateNicknameDto) {
        Long memberId = Long.parseLong(authentication.getName());
        AuthResponseDto.UpdateNickname updateNicknameResDto = authService.updateNickname(memberId, updateNicknameDto.getNickname());
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), updateNicknameResDto);
    }

    @Operation(summary = "마이페이지 회원 정보 조회", description = "마이페이지 회원 정보(아바타, 닉네임, 포인트) 조회")
    @GetMapping("/info")
    public ApiResponse<AuthResponseDto.MemberInfo> getMyPageInfo(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        AuthResponseDto.MemberInfo memberInfo = authService.getMemberInfo(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), memberInfo);
    }
}