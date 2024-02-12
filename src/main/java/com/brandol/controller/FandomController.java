package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.FandomResponseDto;
import com.brandol.service.FandomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "팬덤 게시판 관련 API")
public class FandomController {

    private final FandomService fandomService;

    @Operation(summary = "팬덤 컬처 게시판 전체조회 ",description ="팬덤 컬처 게시물 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/fandom-cultures")
    public ApiResponse<List<FandomResponseDto.FandomDto>>allFandomCultures(@PathVariable("brandId")Long brandId,@RequestParam("page")Integer pageNumber){
        List<FandomResponseDto.FandomDto> fandomDtoList = fandomService.showAllFandomCultures(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), fandomDtoList);
    }

    @Operation(summary = "팬덤 아나운스먼트 게시판 전체조회 ",description ="팬덤 아나운스먼트 게시물 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/fandom-announcements")
    public ApiResponse<List<FandomResponseDto.FandomDto>>allFandomAnnouncements(@PathVariable("brandId")Long brandId,@RequestParam("page")Integer pageNumber){
        List<FandomResponseDto.FandomDto> fandomDtoList = fandomService.showAllFandomAnnouncement(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), fandomDtoList);
    }

    @Operation(summary = "팬덤 게시글 조회 ",description ="팬덤 게시글 상세조회")
    @GetMapping("/brands/fandoms/{fandomId}")
    public ApiResponse<FandomResponseDto.FandomDto> FandomArticle(@PathVariable("fandomId")Long fandomId){
        FandomResponseDto.FandomDto dto = fandomService.showOneFandomArticle(fandomId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

    @Operation(summary = "팬덤 게시글 좋아요 등록",description ="팬덤 게시글 좋아요 등록")
    @GetMapping("/brands/fandoms/{fandomId}/like")
    public ApiResponse<String> FandomLike(@PathVariable("fandomId")Long fandomId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long fandomLikesId = fandomService.fandomLike(fandomId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),"FandomLikesId: "+fandomLikesId);
    }

    @Operation(summary = "팬덤 게시글 좋아요 취소",description ="팬덤 게시글 좋아요 취소")
    @GetMapping("/brands/fandoms/{fandomId}/like-cancel")
    public ApiResponse<String> FandomLikeCancel(@PathVariable("fandomId")Long fandomId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long fandomLikesId = fandomService.fandomLikeCancel(fandomId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),"FandomLikesId: "+fandomLikesId);
    }
}
