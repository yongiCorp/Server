package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Member;
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

    private final BrandService brandService;
    private final MemberService memberService;
    private final ContentsService contentsService;
    private final MemberBrandService memberBrandService;

    @PostMapping("users/my-board-list/new") // 멤버 브랜드 리스트 추가 처리
    public ApiResponse<String> addMemberBrandList(@RequestParam Long memberId, @RequestBody @Valid AddMemberBrandRequest request){
        memberService.addMemberBrandList(memberId, request.getBrandId());
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), null);
    }


    @GetMapping("/users/main")
    public ApiResponse<MemberMainPageResponse> memberMain(@RequestParam Long memberId) {
        MemberMainPageResponse response = memberBrandService.createMemberMainPage(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), response);
    }

        /*
    @GetMapping("/users/main") // 유저 메인페이지 응답
    public ApiResponse<MemberMainPageResponse> memberMain(@RequestParam Long memberId) {

        List<Brand> mainBannerBrands = brandService.getMainBannerBrands();
        Map<String, Object> mainBanner = MainBanners.createMainBanners(mainBannerBrands);

        List<Contents> subBannersContents = contentsService.findRecentEvents(10); // 서브배너 최대 개수 10개 제한
        Map<String,Object> subBanner = SubBanners.createSubBanners(subBannersContents);

        List<Brand> memberBrandList = memberService.findAllBrandByMemberId(memberId);
        Map<String, Object> brandList = BrandList.createBrandList(memberBrandList);

        MemberMainPageResponse result = MemberMainPageResponse.builder()
                .mainBanners(mainBanner)
                .subBanners(subBanner)
                .brandList(brandList)
                .build();

        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

     */
}
