package com.brandol.controller;

import com.brandol.dto.request.CommentRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 관련 API")
public class CommentController {
    @Operation(summary = "팬덤 작성 댓글 작성",description ="멤버가 팬덤게시판 게시글에 댓글을 생성" )
    @Parameter(name = "memberId",description = "[임시]유저를 구분하는 유저 ID로 이후 로그인 서비스 도입시 토큰 대체")
    @PostMapping("comments/fandom/{fandom_id}/new")
    public void addNewFandomComment(@RequestBody CommentRequestDto.addFandomComment dto, @PathVariable("fandom_id")Long fandomId, @RequestParam("memberId")Long memberId){

    }
}
