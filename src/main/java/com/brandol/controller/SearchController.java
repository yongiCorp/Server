package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.SearchMainResponseDto;
import com.brandol.service.SearchService;
import com.brandol.validation.annotation.ExistBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @Operation(summary = "검색 메인 페이지 조회",description ="브랜드, 유저, 컨텐츠, 아바타 스토어(아이템) 랜덤 3개씩 간략 조회" )
    @GetMapping(value = "/search/main")
    public ApiResponse<SearchMainResponseDto.SearchMainAllDto> searchMain() {
        SearchMainResponseDto.SearchMainAllDto searchmainresponse = searchService.makeSearchMainPage();
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), searchmainresponse);
    }




}
