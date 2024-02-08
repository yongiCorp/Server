package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.CommentConverter;
import com.brandol.domain.Contents;
import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityComment;
import com.brandol.domain.mapping.ContentsComment;
import com.brandol.domain.mapping.FandomComment;
import com.brandol.dto.request.CommentRequestDto;
import com.brandol.dto.response.CommentResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        fandomCommentRepository.findById(commentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_COMMENT)); //commentId 존재여부 확인
        FandomComment fandomComment = FandomComment.builder()
                .parentId(commentId)
                .depth(1L) // 대댓글 Depth:1
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
        contentsCommentRepository.findById(commentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_COMMENT)); //commentId 존재여부 확인

        ContentsComment contentsComment = ContentsComment.builder()
                .parentId(commentId)
                .depth(1L) // 대댓글 Depth:1
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
        communityCommentRepository.findById(commentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_COMMENT)); //commentId 존재여부 확인

        CommunityComment communityComment = CommunityComment.builder()
                .parentId(commentId)
                .depth(1L)
                .content(dto.getContent())
                .isDeleted(false)
                .writer(member)
                .community(community)
                .build();

        communityCommentRepository.save(communityComment);
        community.updateComments(community.getComments()+1); // 커뮤니티 게시물의 댓글 개수 업데이트 (더티체킹 활용)
        return communityComment.getId();
    }

    public List<CommentResponseDto.CommentPackageDto> showAllFandomComment(Long fandomId){

        List<FandomComment> fandomCommentList= fandomCommentRepository.findAllByFandomId(fandomId); //해당 게시글의 전체 댓글 조회
        List<FandomComment> parentCommentList = fandomCommentList.stream().filter(fc-> fc.getDepth() == 0).collect(Collectors.toList()); //부모 댓글
        List<FandomComment> childCommentList = fandomCommentList.stream().filter(fc->fc.getDepth() == 1 ).collect(Collectors.toList()); //자식 댓글

        Map<Long,List<FandomComment>> parentChildPairMap =new LinkedHashMap<>(); // key: 부모댓글, value: 자식댓글 리스트

        for(int i=0 ; i<fandomCommentList.size();i++){
            Long key = fandomCommentList.get(i).getId();
            parentChildPairMap.put(key,childCommentList.stream().filter(fc ->fc.getParentId() == key).collect(Collectors.toList()));
        }

        List<CommentResponseDto.CommentPackageDto> results = new ArrayList<>();
        for(int i=0; i<parentCommentList.size();i++){
            Long parentId = parentCommentList.get(i).getParentId();
           CommentResponseDto.CommentPackageDto dto = CommentConverter.toCommentPackageDto(parentCommentList.get(i),parentChildPairMap.get(parentId));
           results.add(dto);
        }

        return results;
    }
}
