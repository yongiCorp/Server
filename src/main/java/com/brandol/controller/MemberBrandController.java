package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.request.AddMemberBrandRequest;
import com.brandol.dto.response.MemberMainPageResponse;
import com.brandol.dto.subDto.BrandList;
import com.brandol.dto.subDto.MainBanners;
import com.brandol.dto.subDto.SubBanners;
import com.brandol.service.BrandService;
import com.brandol.service.ContentsService;
import com.brandol.service.MemberBrandService;
import com.brandol.service.MemberService;
import com.brandol.validation.annotation.ExistBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberBrandController {

    private final MemberService memberService;
    private final MemberBrandService memberBrandService;

    @Operation(summary = "브랜드 리스트 등록",description ="유저가 특정 브랜드를 자신의 브랜드-리스트에 추가" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @PostMapping("users/my-board-list/subscribe") // 멤버 브랜드 리스트 추가 처리
    public ApiResponse<String> addMemberBrandList(@RequestParam Long memberId, @RequestBody @Valid AddMemberBrandRequest request){
        memberService.addMemberBrandList(memberId, request.getBrandId());
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), null);
    }

    @Operation(summary = "브랜드 리스트 삭제",description ="유저가 특정 브랜드를 자신의 브랜드-리스트에서 삭제" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @Parameter(name = "brandId",description = "삭제 대상 브랜드의 ID")
    @PostMapping("users/my-board-list/unsubscribe/{brandId}")
    public ApiResponse<String> MemberBrandListStatusToUnsubscribed(@RequestParam Long memberId,@ExistBrand @PathVariable("brandId")Long brandId){
        MemberBrandList memberBrandList = memberBrandService.MemberBrandListStatusToUnsubscribed(memberId,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._CREATED.getMessage(), "처리 성공");
    }

    @Operation(summary = "1페이지 로드",description ="1페이지 진입시 호출해야 하는 API" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @GetMapping("/users/main")
    public ApiResponse<MemberMainPageResponse> memberMain(@RequestParam Long memberId) {
        MemberMainPageResponse response = memberBrandService.createMemberMainPage(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), response);
    }

}
