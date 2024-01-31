package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.Brand;
import com.brandol.dto.request.BrandRequestDto;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.service.BrandService;
import com.brandol.validation.annotation.ExistBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "브랜드 관련 API")
public class BrandController {

    private final BrandService brandService;


    @Operation(summary = "브랜드 등록",description ="브랜돌 서비스에 브랜드를 신규 등록하는 함수" )
    @PostMapping(value = "/brands/new",consumes = "multipart/form-data") // 브랜드 신규 등록 함수
    private ApiResponse<Brand> addNewBrand(@ModelAttribute BrandRequestDto.addBrand request){
        System.out.println(request.getName());
        System.out.println(request.getDescription());
        Brand brand = brandService.createBrand(request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), brand);
    }

    @Operation(summary = "브랜드 공통 헤더 조회",description ="브랜드 프로필,배경이미지, 구독자 수등 브랜드 상세정보 헤더를 조회" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/header")
    public ApiResponse<BrandResponseDto.BrandHeaderDto> showBrandHeader(@PathVariable("brandId")Long brandId, @RequestParam("memberId")Long memberId){
        BrandResponseDto.BrandHeaderDto brandHeaderDto = brandService.makeBrandCommonHeader(brandId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), brandHeaderDto);
    }
    @Operation(summary = "브랜드 팬덤 조회",description ="브랜드 팬덤에 종속된 브랜드 컬처, 브랜드 공지사항 최신 2건을 조회" )
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/fandom")
    public ApiResponse<BrandResponseDto.BrandFandomDto> showBrandFandom(@ExistBrand @PathVariable("brandId")Long brandId) {
        BrandResponseDto.BrandFandomDto brandFandomDto = brandService.makeBrandFandomBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), brandFandomDto);
    }

    @Operation(summary = "브랜드 콘텐츠 조회",description = "브랜드 콘텐츠에 종속된 브랜드 이벤트, 브랜드 카드뉴스, 브랜드 비디오 최신 2건을 조회")
    @Parameter(name = "brandId",description = "조회 대상 브랜드의 ID")
    @GetMapping(value = "/brands/{brandId}/contents")
    public ApiResponse<BrandResponseDto.BrandContentsDto> showBrandContents(@ExistBrand @PathVariable("brandId")Long brandId){
        BrandResponseDto.BrandContentsDto brandContentsDto = brandService.makeBrandContentsBody(brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), brandContentsDto);
    }
}
