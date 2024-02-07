package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.domain.Contents;
import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityComment;
import com.brandol.domain.mapping.ContentsComment;
import com.brandol.domain.mapping.FandomComment;
import com.brandol.dto.request.CommentRequestDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final FandomRepository fandomRepository;
    private final ContentsRepository contentsRepository;
    private final CommunityRepository communityRepository;
    private final FandomCommentRepository fandomCommentRepository;
    private final ContentsCommentRepository contentsCommentRepository;
    private final CommunityCommentRepository communityCommentRepository;

    @Transactional
    public Long createFandomComment(CommentRequestDto.addComment dto, Long fandomId, Long memberId){

        Fandom fandom = fandomRepository.findById(fandomId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM));

        Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        FandomComment fandomComment = FandomComment.builder()
                .parentId(null)
                .depth(0L)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .fandom(fandom)
                .build();
        fandomCommentRepository.save(fandomComment);
        fandomComment.addParentId(fandomComment.getId()); // parentId에 자기 자신의 아이디를 추가함 (더티체킹 활용)
        fandom.updateComments(fandom.getComments()+1); // 팬덤 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return fandomComment.getId();
    }

    @Transactional
    public Long crateContentsComment(CommentRequestDto.addComment dto, Long contentsId, Long memberId){

        Contents contents = contentsRepository.findById(contentsId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS));

        Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));

        ContentsComment contentsComment = ContentsComment.builder()
                .parentId(null)
                .depth(0L)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .contents(contents)
                .build();
        contentsCommentRepository.save(contentsComment);
        contentsComment.addParentId(contentsComment.getId()); // parentId에 자기 자신의 아이디를 추가함 (더티체킹 활용)
        contents.updateComments(contents.getComments()+1); // 콘텐츠 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return contentsComment.getId();
    }

    @Transactional
    public Long createCommunityComment(CommentRequestDto.addComment dto, Long communityId, Long memberId){

        Community community = communityRepository.findById(communityId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY));
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));

        CommunityComment communityComment = CommunityComment.builder()
                .parentId(null)
                .depth(0L)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .community(community)
                .build();

        communityCommentRepository.save(communityComment);
        communityComment.addParentId(communityComment.getId()); // parentId에 자기 자신의 아이디를 추가함 (더티체킹 활용)
        community.updateComments(community.getComments()+1); // 커뮤니티 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return  communityComment.getId();
    }

    @Transactional
    public Long createFandomNestedComment(CommentRequestDto.addComment dto,Long fandomId,Long commentId,Long memberId){

        Fandom fandom = fandomRepository.findById(fandomId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM)); // 댓글이 게시될 팬덤 게시판
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER)); // 댓글 작성자
        List<FandomComment> fandomCommentList = fandomCommentRepository.findFandomCommentsByParentId(commentId);
        if(fandomCommentList.isEmpty()){throw new ErrorHandler(ErrorStatus._NOT_EXIST_COMMENT);}
        Long lastDepth = fandomCommentList.stream().max(Comparator.comparingLong(FandomComment::getDepth)).get().getDepth(); // 마지막 depth 찾기

        if (lastDepth == 0) { // 대댓글 Depth:1
            lastDepth++;
        }

        FandomComment fandomComment = FandomComment.builder()
                .parentId(commentId)
                .depth(lastDepth)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .fandom(fandom)
                .build();

        fandomCommentRepository.save(fandomComment);
        fandom.updateComments(fandom.getComments()+1); // 팬덤 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return fandomComment.getId();
    }

    @Transactional
    public Long createContentsNestedComment(CommentRequestDto.addComment dto, Long contentsId,Long commentId,Long memberId){

        Contents contents = contentsRepository.findById(contentsId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS));
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        List<ContentsComment> contentsCommentList = contentsCommentRepository.findContentsCommentsByParentId(commentId);
        if(contentsCommentList.isEmpty()){throw new ErrorHandler(ErrorStatus._NOT_EXIST_COMMENT);}
        Long lastDepth = contentsCommentList.stream().max(Comparator.comparingLong(ContentsComment::getDepth)).get().getDepth();

        if(lastDepth == 0){ // 대댓글 Depth:1
            lastDepth++;
        }

        ContentsComment contentsComment = ContentsComment.builder()
                .parentId(commentId)
                .depth(lastDepth)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .contents(contents)
                .build();
        contentsCommentRepository.save(contentsComment);
        contents.updateComments(contents.getComments()+1); // 콘텐츠 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return contentsComment.getId();
    }

    @Transactional
    public Long createCommunityNestedComment(CommentRequestDto.addComment dto, Long communityId,Long commentId,Long memberId){

        Community community = communityRepository.findById(communityId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY));
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        List<CommunityComment> communityCommentList = communityCommentRepository.findCommunityCommentsByParentId(commentId);
        if(communityCommentList.isEmpty()){throw new ErrorHandler(ErrorStatus._NOT_EXIST_COMMENT);}
        Long lastDepth = communityCommentList.stream().max(Comparator.comparingLong(CommunityComment::getDepth)).get().getDepth();

        if(lastDepth == 0){ // 대댓글 Depth:1
            lastDepth++;
        }

        CommunityComment communityComment = CommunityComment.builder()
                .parentId(commentId)
                .depth(lastDepth)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .community(community)
                .build();

        communityCommentRepository.save(communityComment);
        community.updateComments(community.getComments()+1); // 커뮤니티 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return communityComment.getId();
    }
}
