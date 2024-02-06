package com.brandol.controller;


import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.CommunityResponseDto;
import com.brandol.dto.response.ContentsResponseDto;
import com.brandol.service.CommunityService;
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
@Tag(name = "커뮤니티 게시판 관련 API")
public class CommunityController {

    private final CommunityService communityService;

    @Operation(summary = "커뮤니티 자유게시판 전체조회 ",description ="커뮤니티 자유게시판 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/community-free")
    public ApiResponse<List<CommunityResponseDto.CommunityDto>> allCommunityAll(@PathVariable("brandId")Long brandId, @RequestParam("page")Integer pageNumber){
        List<CommunityResponseDto.CommunityDto> communityDtoList = communityService.showAllCommunityAll(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), communityDtoList);
    }

    @Operation(summary = "커뮤니티 피드백게시판 전체조회 ",description ="커뮤니티 피드게시판 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/community-feedback")
    public ApiResponse<List<CommunityResponseDto.CommunityDto>> allCommunityFeedback(@PathVariable("brandId")Long brandId, @RequestParam("page")Integer pageNumber){
        List<CommunityResponseDto.CommunityDto> communityDtoList = communityService.showAllCommunityFeedBack(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), communityDtoList);
    }
}
