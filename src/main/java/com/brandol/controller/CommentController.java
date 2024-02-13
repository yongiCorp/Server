package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.dto.request.CommentRequestDto;
import com.brandol.dto.response.CommentResponseDto;
import com.brandol.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "팬덤 댓글 작성",description ="멤버가 팬덤 게시판 게시글에 댓글을 생성" )
    @PostMapping("/fandom/{fandom_id}/comments/new")
    public ApiResponse<String> addNewFandomComment(@RequestBody CommentRequestDto.addComment dto, @PathVariable("fandom_id")Long fandomId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long commentId = commentService.createFandomComment(dto,fandomId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"FandomCommentId: "+ commentId);
    }

    @Operation(summary = "팬덤 댓글 좋아요",description ="팬덤 게시판 댓글 좋어요 등록" )
    @PostMapping("/fandom/comments/{commentId}/like")
    public ApiResponse<String> fandomCommentLike(@PathVariable("commentId")Long commentId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long fandomCommentLikeId = commentService.fandomCommentLike(commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(), "FandomCommentLikeId: "+fandomCommentLikeId);
    }

    @Operation(summary = "팬덤 댓글 좋아요 취소",description ="팬덤 게시판 댓글 좋어요 취소" )
    @PostMapping("/fandom/comments/{commentId}/like-cancel")
    public ApiResponse<String> fandomCommentLikeCancel(@PathVariable("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long fandomCommentLikeId = commentService.fandomCommentLikeCancel(commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(), "FandomCommentLikeId: "+fandomCommentLikeId);
    }

    @Operation(summary = "콘텐츠 댓글 작성",description ="멤버가 콘텐츠 게시판 게시글에 댓글을 생성" )
    @PostMapping("/contents/{contents_id}/comments/new")
    public ApiResponse<String> addNewContentsComment(@RequestBody CommentRequestDto.addComment dto, @PathVariable("contents_id")Long contentsId, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long commentId = commentService.crateContentsComment(dto,contentsId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"ContentsCommentId: "+ commentId);
    }

    @Operation(summary = "콘텐츠 댓글 좋아요",description ="콘텐츠 게시판 댓글 좋어요 등록" )
    @PostMapping("/contents/comments/{commentId}/like")
    public ApiResponse<String> contentsCommentLike(@PathVariable("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long contentsCommentLikeId = commentService.contentsCommentLike(commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(), "ContentsCommentLikeId: "+contentsCommentLikeId);
    }

    @Operation(summary = "콘텐츠 댓글 좋아요 취소",description ="콘텐츠 게시판 댓글 좋어요 취소" )
    @PostMapping("/contents/comments/{commentId}/like-cancel")
    public ApiResponse<String> contentsCommentLikeCancel(@PathVariable("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long contentsCommentLikeId = commentService.contentsCommentLikeCancel(commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(), "ContentsCommentLikeId: "+contentsCommentLikeId);
    }

    @Operation(summary = "커뮤니티 댓글 작성",description ="멤버가 커뮤니티 게시판 게시글에 댓글을 생성" )
    @PostMapping("/communities/{community_id}/comments/new")
    public ApiResponse<String> addNewCommunityComment(@RequestBody CommentRequestDto.addComment dto, @PathVariable("community_id")Long communityId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long commentId = commentService.createCommunityComment(dto,communityId,memberId);
        return  ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"CommunityCommentId: "+ commentId);
    }

    @Operation(summary = "커뮤니티 댓글 좋아요",description ="커뮤니티 게시판 댓글 좋어요 등록" )
    @PostMapping("/community/comments/{commentId}/like")
    public ApiResponse<String> communityCommentLike(@PathVariable("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long communityCommentLikeId = commentService.communityCommentLike(commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(), "CommunityCommentLikeId: "+communityCommentLikeId);
    }

    @Operation(summary = "커뮤니티 댓글 좋아요 취소",description ="커뮤니티 게시판 댓글 좋어요 취소" )
    @PostMapping("/community/comments/{commentId}/like-cancel")
    public ApiResponse<String> communityCommentLikeCancel(@PathVariable("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long contentsLikeId = commentService.communityCommentLikeCancel(commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(), "CommunityCommentLikeId: "+contentsLikeId);
    }

    @Operation(summary = "팬덤 대댓글 작성",description ="멤버가 팬덤 게시판 게시글에 대댓글을 생성" )
    @PostMapping("/fandom/{fandom_id}/comments/{commentId}")
    public ApiResponse<String> addNewFandomNestedComment(@RequestBody CommentRequestDto.addComment dto, @PathVariable("fandom_id")Long fandomId,@RequestParam("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long  nestedCommentId = commentService.createFandomNestedComment(dto,fandomId,commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"FandomCommentId: "+ nestedCommentId);
    }

    @Operation(summary = "콘텐츠 대댓글 작성",description ="멤버가 콘텐츠 게시판 게시글에 대댓글을 생성" )
    @PostMapping("/contents/{contents_id}/comments/{commentId}")
    public ApiResponse<String> addNewContentsNestedComment(@RequestBody CommentRequestDto.addComment dto, @PathVariable("contents_id")Long contentsId,@RequestParam("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long  nestedCommentId = commentService.createContentsNestedComment(dto,contentsId,commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"ContentsCommentId: "+ nestedCommentId);
    }

    @Operation(summary = "커뮤니티 대댓글 작성",description ="멤버가 커뮤니티 게시판 게시글에 대댓글을 생성" )
    @PostMapping("/communities/{community_id}/comments/{commentId}")
    public ApiResponse<String> addNewCommunityNestedComment(@RequestBody CommentRequestDto.addComment dto, @PathVariable("community_id")Long communityId,@RequestParam("commentId")Long commentId,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        Long  nestedCommentId = commentService.createCommunityNestedComment(dto,communityId,commentId,memberId);
        return ApiResponse.onSuccess(SuccessStatus._CREATED.getCode(),SuccessStatus._CREATED.getMessage(),"CommunityCommentId: "+ nestedCommentId);
    }

    @Operation(summary = "팬덤 게시글 댓글 전체 조회",description ="해당 팬덤 게시글의 댓글 전체 조회" )
    @Parameter(name = "fandomId",description = "대상 팬덤게시글의 id")
    @GetMapping("/fandoms/{fandomId}/comments/all")
    public ApiResponse<List<CommentResponseDto.CommentPackageDto>> showAllFandomComments(@PathVariable("fandomId")Long fandomId){
        List<CommentResponseDto.CommentPackageDto> dto = commentService.showAllFandomComment(fandomId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

    @Operation(summary = "콘텐츠 게시글 댓글 전체 조회",description ="해당 콘텐츠 게시글의 댓글 전체 조회" )
    @Parameter(name = "contentsId",description = "대상 콘텐츠 게시글의 id")
    @GetMapping("/contents/{contentsId}/comments/all")
    public ApiResponse<List<CommentResponseDto.CommentPackageDto>> showAllContentsComments(@PathVariable("contentsId")Long contentsId){
        List<CommentResponseDto.CommentPackageDto> dto = commentService.showAllContentsComment(contentsId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }

    @Operation(summary = "커뮤니티 게시글 댓글 전체 조회",description ="해당 커뮤니티 게시글의 댓글 전체 조회" )
    @Parameter(name = "communityId",description = "대상 커뮤니티 게시글의 id")
    @GetMapping("/communities/{communityId}/comments/all")
    public ApiResponse<List<CommentResponseDto.CommentPackageDto>> showAllCommunityComments(@PathVariable("communityId")Long communityId){
        List<CommentResponseDto.CommentPackageDto> dto = commentService.showAllCommunityComment(communityId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), dto);
    }


}