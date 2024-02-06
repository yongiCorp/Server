package com.brandol.controller;


import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.ContentsResponseDto;
import com.brandol.service.ContentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "콘텐츠 게시판 관련 API")
public class ContentsController {

    private final ContentsService contentsService;

    @Operation(summary = "콘텐츠 카드뉴스 게시판 전체조회 ",description ="팬덤 컬체 게시물 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/contents-cardnews/all")
    public ApiResponse<List<ContentsResponseDto.ContentsDto>> allContentsCardNews(@PathVariable("brandId")Long brandId,@RequestParam("page")Integer pageNumber){
        List<ContentsResponseDto.ContentsDto> contentsDtoList = contentsService.showAllContentsCardNews(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), contentsDtoList);
    }

    @Operation(summary = "콘텐츠 이벤트 게시판 전체조회 ",description ="팬덤 컬체 게시물 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/contents-event/all")
    public ApiResponse<List<ContentsResponseDto.ContentsDto>> allContentsEvents(@PathVariable("brandId")Long brandId,@RequestParam("page")Integer pageNumber){
        List<ContentsResponseDto.ContentsDto> contentsDtoList = contentsService.showAllContentsEvents(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), contentsDtoList);
    }

    @Operation(summary = "콘텐츠 비디오 게시판 전체조회 ",description ="팬덤 컬체 게시물 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/contents-video/all")
    public ApiResponse<List<ContentsResponseDto.ContentsDto>> allContentsVideos(@PathVariable("brandId")Long brandId, @RequestParam("page")Integer pageNumber){
        List<ContentsResponseDto.ContentsDto> contentsDtoList = contentsService.showAllContentsVideos(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), contentsDtoList);
    }

}
