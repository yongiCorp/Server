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
        return  communityComment.getId();
    }

}
