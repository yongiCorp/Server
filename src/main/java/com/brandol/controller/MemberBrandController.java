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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberBrandController {

    private final MemberService memberService;
    private final MemberBrandService memberBrandService;

    @PostMapping("users/my-board-list/subscribe") // 멤버 브랜드 리스트 추가 처리
    public ApiResponse<String> addMemberBrandList(@RequestParam Long memberId, @RequestBody @Valid AddMemberBrandRequest request){
        memberService.addMemberBrandList(memberId, request.getBrandId());
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), null);
    }

    @PostMapping("users/my-board-list/unsubscribe/{brandId}")
    public ApiResponse<String> MemberBrandListStatusToUnsubscribed(@RequestParam Long memberId,@PathVariable("brandId")Long brandId){
        MemberBrandList memberBrandList = memberBrandService.MemberBrandListStatusToUnsubscribed(memberId,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._CREATED.getMessage(), "처리 성공");
    }

    @GetMapping("/users/main")
    public ApiResponse<MemberMainPageResponse> memberMain(@RequestParam Long memberId) {
        MemberMainPageResponse response = memberBrandService.createMemberMainPage(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), response);
    }

}
