package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.domain.Brand;
import com.brandol.dto.request.AddBrandRequest;
import com.brandol.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "브랜드 등록",description ="브랜돌 서비스에 브랜드를 신규 등록하는 함수" )
    @Parameters({
            @Parameter(name="name",description = "브랜드 이름"),
            @Parameter(name = "description" , description = "브랜드 소개"),
            @Parameter(name = "profileImage", description = "프로필 이미지 파일"),
            @Parameter(name = "backgroundImage",description = "배경 이미지 파일")
    })
    @PostMapping(value = "/brands/new",consumes = "multipart/form-data") // 브랜드 신규 등록 함수
    private ApiResponse<Brand> addNewBrand(@ModelAttribute AddBrandRequest request){
        Brand brand = brandService.createBrand(request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(), brand);
    }
}
