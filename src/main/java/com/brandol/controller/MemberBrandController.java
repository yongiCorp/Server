package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.request.MemberBrandRequestDto;
import com.brandol.service.MemberBrandService;
import com.brandol.service.MemberMissionService;
import com.brandol.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "브랜드 리스트 관련 API", description = "회원 리스트에 브랜드 추가, 삭제, ")
public class MemberBrandController {

    private final MemberService memberService;
    private final MemberBrandService memberBrandService;
    private final MemberMissionService memberMissionService;

    @Operation(summary = "브랜드 리스트 등록",description ="유저가 특정 브랜드를 자신의 브랜드-리스트에 추가" )
    @PostMapping("users/my-board-list/subscribe/{brandId}") // 멤버 브랜드 리스트 추가 처리
    public ApiResponse<String> addMemberBrandList(@PathVariable("brandId")Long brandId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        memberService.addMemberBrandList(memberId, brandId);
        memberMissionService.checkBrandMission(memberId, brandId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), null);
    }

    @Operation(summary = "브랜드 리스트 삭제",description ="유저가 특정 브랜드를 자신의 브랜드-리스트에서 삭제" )
    @Parameter(name = "brandId",description = "삭제 대상 브랜드의 ID")
    @PostMapping("users/my-board-list/unsubscribe/{brandId}")
    public ApiResponse<String> MemberBrandListStatusToUnsubscribed(@PathVariable("brandId")Long brandId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        MemberBrandList memberBrandList = memberBrandService.MemberBrandListStatusToUnsubscribed(memberId,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._CREATED.getMessage(), "처리 성공");
    }

}
