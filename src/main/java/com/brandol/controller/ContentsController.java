package com.brandol.controller;


import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.response.ContentsResponseDto;
import com.brandol.service.ContentsService;
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
@Tag(name = "콘텐츠 게시판 관련 API")
public class ContentsController {

    private final ContentsService contentsService;

    @Operation(summary = "콘텐츠 카드뉴스 게시판 전체조회 ",description ="콘텐츠 카드뉴스 게시판 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/contents-cardnews")
    public ApiResponse<List<ContentsResponseDto.ContentsDto>> allContentsCardNews(@PathVariable("brandId")Long brandId,@RequestParam("page")Integer pageNumber){
        List<ContentsResponseDto.ContentsDto> contentsDtoList = contentsService.showAllContentsCardNews(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), contentsDtoList);
    }

    @Operation(summary = "콘텐츠 이벤트 게시판 전체조회 ",description ="콘텐츠 이벤트 게사판 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/contents-events")
    public ApiResponse<List<ContentsResponseDto.ContentsDto>> allContentsEvents(@PathVariable("brandId")Long brandId,@RequestParam("page")Integer pageNumber){
        List<ContentsResponseDto.ContentsDto> contentsDtoList = contentsService.showAllContentsEvents(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), contentsDtoList);
    }

    @Operation(summary = "콘텐츠 비디오 게시판 전체조회 ",description ="콘텐츠 비디오 게시판 전체 조회(페이징: 0페이지 부터 시작)")
    @GetMapping("/brands/{brandId}/contents-videos")
    public ApiResponse<List<ContentsResponseDto.ContentsDto>> allContentsVideos(@PathVariable("brandId")Long brandId, @RequestParam("page")Integer pageNumber){
        List<ContentsResponseDto.ContentsDto> contentsDtoList = contentsService.showAllContentsVideos(pageNumber,brandId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), contentsDtoList);
    }

    @Operation(summary = "콘텐츠 게시글 조회 ",description ="콘텐츠 게시글 상세조회")
    @GetMapping("/brands/contents/{contentsId}")
    public ApiResponse<ContentsResponseDto.ContentsDto> contentsArticle(@PathVariable("contentsId")Long contentsId){
        ContentsResponseDto.ContentsDto dto = contentsService.showOneContentsArticle(contentsId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

    @Operation(summary = "콘텐츠 게시글 좋아요 등록",description ="콘텐츠 게시글 좋아요 등록")
    @GetMapping("/brands/contents/{contentsId}/like")
    public ApiResponse<String> FandomLike(@PathVariable("contentsId")Long contentsId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long contentsLikesId = contentsService.contentsLike(contentsId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),"ContentsLikesId: "+contentsLikesId);
    }

    @Operation(summary = "콘텐츠 게시글 좋아요 취소",description ="콘텐츠 게시글 좋아요 취소")
    @GetMapping("/brands/contents/{contentsId}/like-cancel")
    public ApiResponse<String> FandomLikeCancel(@PathVariable("contentsId")Long contentsId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long contentsLikesId = contentsService.contentsLikeCancel(contentsId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(), SuccessStatus._CREATED.getMessage(),"ContentsLikesId: "+contentsLikesId);
    }
}
