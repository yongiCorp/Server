package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.MemberResponseDto;
import com.brandol.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "멤버 관련 API", description = "홈페이지, 검색 페이지 등 조회")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "홈페이지 조회",description ="1페이지 진입시 호출해야 하는 API" )
    @GetMapping("/users/main")
    public ApiResponse<MemberResponseDto.MemberMainDto> memberMain(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberResponseDto.MemberMainDto memberMainDto = memberService.makeMemberMain(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), memberMainDto);
    }
}
