package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.dto.response.SearchResponseDto;
import com.brandol.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;



    @Operation(summary = "검색 메인 페이지 조회",description ="브랜드, 유저, 컨텐츠, 아바타 스토어(아이템) 랜덤 3개씩 간략 조회" )
    @GetMapping(value = "/search/main")
    public ApiResponse<SearchResponseDto.SearchMainAllDto> searchMain() {
        SearchResponseDto.SearchMainAllDto searchmainresponse = searchService.makeSearchMainPage();
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), searchmainresponse);
    }

    @Operation(summary = "검색 더보기 페이지 조회 - 브랜드", description ="브랜드 전체 랜덤 상세 조회" )
    @GetMapping(value = "/search/detail/brands")
    public ApiResponse<SearchResponseDto.SearchDetailBrandAllDto> searchDetailBrand(){
        SearchResponseDto.SearchDetailBrandAllDto searchDetailBrandDto = searchService.makeSearchDetailBrandPage();
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), searchDetailBrandDto);
    }

    @Operation(summary = "검색 더보기 페이지 조회 - 유저", description ="유저 전체 랜덤 상세 조회" )
    @GetMapping(value = "/search/detail/users")
    public ApiResponse<SearchResponseDto.SearchDetailUserAllDto> searchDetailUser(){
        SearchResponseDto.SearchDetailUserAllDto searchDetailUserDto = searchService.makeSearchDetailUserPage();
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), searchDetailUserDto);
    }

    @Operation(summary = "검색 더보기 페이지 조회 - 컨텐츠", description ="컨텐츠 전체 랜덤 상세 조회" )
    @GetMapping(value = "/search/detail/contents")
    public ApiResponse<SearchResponseDto.SearchDetailContentsAllDto> searchDetailContents(){
        SearchResponseDto.SearchDetailContentsAllDto searchDetailContentsDto = searchService.makeSearchDetailContentsPage();
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), searchDetailContentsDto);
    }

    @Operation(summary = "검색 더보기 페이지 조회 - 아바타 스토어의 헤더", description ="유저의 아바타와 보유 포인트 조회" )
    @Parameter(name = "memberId",description = "유저의 아바타와 포인트 조회를 위한 ID")
    @GetMapping(value = "/search/detail/avatar-store/header/{memberId}")
    public ApiResponse<SearchResponseDto.SearchDetailAvatarStoreHeaderDto> searchDetailAvatarStoreHeader(@PathVariable("memberId")Long memberId){
        SearchResponseDto.SearchDetailAvatarStoreHeaderDto searchDetailAvatarStoreHeaderDto = searchService.makeSearchDetailAvatarStoreHeaderPage(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), searchDetailAvatarStoreHeaderDto);
    }

    @Operation(summary = "검색 더보기 페이지 조회 - 아바타 스토어의 바디", description ="아이템 종류별 전체 랜덤 상세 조회" )
    @Parameter(name = "itemPart",description = "아이템을 종류별로 조회 하기 위한 파라미터")
    @GetMapping(value = "/search/detail/avatar-store/body")
    public ApiResponse<SearchResponseDto.Search_Detail_AvatarStore_Body_All_Dto> searchDetailAvatarStoreBody(@RequestParam("itemPart")String itemPart){
        SearchResponseDto.Search_Detail_AvatarStore_Body_All_Dto searchDetailAvatarStoreBodyDto = searchService.makeSearchDetailAvatarStoreBodyPage(itemPart);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(), searchDetailAvatarStoreBodyDto);
    }




}
