package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.Brand;
import com.brandol.dto.request.AddBrandRequest;
import com.brandol.dto.response.BrandCommonHeaderResponse;
import com.brandol.dto.response.BrandContentsBodyResponse;
import com.brandol.dto.response.BrandFandomBodyResponse;
import com.brandol.service.BrandService;
import com.brandol.validation.annotation.ExistBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;


    @Operation(summary = "브랜드 등록",description ="브랜돌 서비스에 브랜드를 신규 등록하는 함수" )
    @PostMapping(value = "/brands/new",consumes = "multipart/form-data") // 브랜드 신규 등록 함수
    private ApiResponse<Brand> addNewBrand(@ModelAttribute AddBrandRequest request){
        Brand brand = brandService.createBrand(request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), brand);
    }

    @Operation(summary = "브랜드 공통 헤더 조회",description ="브랜드 프로필,배경이미지, 구독자 수등 브랜드 상세정보 헤더를 조회" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/header")
    public ApiResponse<BrandCommonHeaderResponse> showBrandHeader(@PathVariable("brandId")Long brandId, @RequestParam("memberId")Long memberId){
        BrandCommonHeaderResponse brandCommonHeaderResponse = brandService.makeBrandCommonHeader(brandId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), brandCommonHeaderResponse);
    }
    @Operation(summary = "브랜드 팬덤 조회",description ="브랜드 팬덤에 종속된 브랜드 컬처, 브랜드 공지사항 최신 2건을 조회" )
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/fandom")
    public ApiResponse<BrandFandomBodyResponse> showBrandFandomBody(@ExistBrand @PathVariable("brandId")Long brandId) {
        BrandFandomBodyResponse brandFandomBody = brandService.makeBrandFandomBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), brandFandomBody);
    }

    @Operation(summary = "브랜드 콘텐츠 조회",description ="브랜드 콘텐츠에 종속된 브랜드 이벤트, 브랜드 카드뉴스, 브랜드 비디오 최신 2건을 조회" )
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/contents")
    public ApiResponse<BrandContentsBodyResponse> showBrandContentsBody(@ExistBrand @PathVariable("brandId")Long brandId) {
        BrandContentsBodyResponse brandContentsBody = brandService.makeBrandContentsBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), brandContentsBody);
    }


}
