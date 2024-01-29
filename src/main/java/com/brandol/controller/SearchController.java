package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.SearchResponse;
import com.brandol.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping(value = "/search/main") // 검색페이지 - 랜덤 3개씩 간략 조회
    public ApiResponse<SearchResponse> searchMain() {
        SearchResponse searchmainresponse = searchService.Makesearchpage();

        return ApiResponse.onSuccess(searchmainresponse);
    }



}
