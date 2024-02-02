package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.MemberMainPageResponse;
import com.brandol.dto.response.MemberResponseDto;
import com.brandol.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "1페이지 로드",description ="1페이지 진입시 호출해야 하는 API" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @GetMapping("/users/main")
    public ApiResponse<MemberResponseDto.MemberMainDto> memberMain(@RequestParam Long memberId) {
        MemberResponseDto.MemberMainDto memberMainDto = memberService.makeMemberMain(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), memberMainDto);
    }

    @Operation(summary = "멤버 작성 글 조회",description ="멤버가 작성한 글을 조회 할때 호출" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @GetMapping("/users/my")
    public ApiResponse<?> memberWritten(@RequestParam Long memberId){
        MemberResponseDto.MemberWrittenArticleMainDto dto = memberService.makeMemberWrittenPage(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

}
