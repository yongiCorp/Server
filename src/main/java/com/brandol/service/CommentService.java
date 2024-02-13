package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.controller.FandomController;
import com.brandol.converter.CommentConverter;
import com.brandol.domain.Contents;
import com.brandol.domain.Fandom;
import com.brandol.domain.Member;
import com.brandol.domain.enums.LikeStatus;
import com.brandol.domain.mapping.*;
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
    private final FandomCommentLikesRepository fandomCommentLikesRepository;
    private final ContentsCommentLikesRepository contentsCommentLikesRepository;
    private final CommunityCommentsLikesRepository communityCommentsLikesRepository;

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
           CommentResponseDto.CommentPackageDto dto = CommentConverter.toFandomCommentPackageDto(parentCommentList.get(i),parentChildPairMap.get(parentId));
           results.add(dto);
        }

        return results;
    }

    public List<CommentResponseDto.CommentPackageDto> showAllContentsComment(Long contentsId){

        List<ContentsComment> contentsCommentList = contentsCommentRepository.findAllByContentsId(contentsId);
        List<ContentsComment> parentCommentList = contentsCommentList.stream().filter(cc-> cc.getDepth() == 0).collect(Collectors.toList()); //부모 댓글
        List<ContentsComment> childCommentList = contentsCommentList.stream().filter(cc->cc.getDepth() == 1).collect(Collectors.toList()); // 자식 댓글

        Map<Long,List<ContentsComment>> parentChildPairMap = new LinkedHashMap<>();

        for(int i=0; i<contentsCommentList.size();i++){
            Long key = contentsCommentList.get(i).getId();
            parentChildPairMap.put(key,childCommentList.stream().filter(cc->cc.getParentId() == key).collect(Collectors.toList()));
        }

        List<CommentResponseDto.CommentPackageDto> results = new ArrayList<>();
        for(int i=0; i<parentCommentList.size();i++){
            Long parentId = parentCommentList.get(i).getParentId();
            CommentResponseDto.CommentPackageDto dto = CommentConverter.toContentsCommentPackageDto(parentCommentList.get(i),parentChildPairMap.get(parentId));
            results.add(dto);
        }
        return results;
    }

    public List<CommentResponseDto.CommentPackageDto> showAllCommunityComment(Long communityId){

        List<CommunityComment> communityCommentList = communityCommentRepository.findAllByCommunityId(communityId);
        List<CommunityComment> parentCommentList = communityCommentList.stream().filter(cc-> cc.getDepth() == 0).collect(Collectors.toList()); //부모 댓글
        List<CommunityComment> childCommentList = communityCommentList.stream().filter(cc->cc.getDepth() == 1).collect(Collectors.toList()); // 자식 댓글

        Map<Long,List<CommunityComment>> parentChildPairMap = new LinkedHashMap<>();

        for(int i=0; i<communityCommentList.size();i++){
            Long key = communityCommentList.get(i).getId();
            parentChildPairMap.put(key,childCommentList.stream().filter(cc->cc.getParentId() == key).collect(Collectors.toList()));
        }

        List<CommentResponseDto.CommentPackageDto> results = new ArrayList<>();
        for(int i=0; i<parentCommentList.size();i++){
            Long parentId = parentCommentList.get(i).getParentId();
            CommentResponseDto.CommentPackageDto dto = CommentConverter.toCommunityCommentPackageDto(parentCommentList.get(i),parentChildPairMap.get(parentId));
            results.add(dto);
        }
        return results;
    }

    @Transactional
    public Long fandomCommentLike(Long fandomCommentId,Long memberId){
        List<FandomCommentLikes> fandomCommentLikesList = fandomCommentLikesRepository.findAllByFandomCommentIdAndMemberId(fandomCommentId,memberId);

        if(fandomCommentLikesList.size()>1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)

        if(fandomCommentLikesList.isEmpty()){ // 기존에 좋아요를 누른 경우가 없는 경우

            Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            FandomComment fandomComment = fandomCommentRepository.findById(fandomCommentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_COMMENT));
            FandomCommentLikes fandomCommentLikes =FandomCommentLikes.builder()
                    .likeStatus(LikeStatus.Continue)
                    .fandomComment(fandomComment)
                    .member(member)
                    .build();
            fandomCommentLikesRepository.save(fandomCommentLikes);
            fandomComment.updateLikes(fandomComment.getLikes()+1);
            return fandomCommentLikes.getId();

        }
        else{//기존에 좋아요를 눌렀다가 취소하고 다시 누른 경우
            FandomCommentLikes fandomCommentLikes = fandomCommentLikesList.get(0);
            fandomCommentLikes.changeLikeStatus(LikeStatus.Continue);
            FandomComment fandomComment = fandomCommentRepository.findById(fandomCommentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_COMMENT));
            fandomComment.updateLikes(fandomComment.getLikes()+1);
            return fandomCommentLikes.getId();
        }
    }

    @Transactional
    public Long fandomCommentLikeCancel(Long fandomCommentId, Long memberId){
        List<FandomCommentLikes> fandomCommentLikesList = fandomCommentLikesRepository.findAllByFandomCommentIdAndMemberId(fandomCommentId,memberId);
        if(fandomCommentLikesList.size() >1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)
        if(fandomCommentLikesList.isEmpty()){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_COMMENT_LIKES);} //팬덤 코멘트 라이크 엔티티가 존재하지 않는 경우
        FandomCommentLikes targetFandomCommentLikes = fandomCommentLikesList.get(0);
        targetFandomCommentLikes.changeLikeStatus(LikeStatus.Cancel); //더티 체킹 활용
        FandomComment fandomComment = fandomCommentRepository.findById(fandomCommentId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_COMMENT));
        fandomComment.updateLikes(fandomComment.getLikes()-1); // 팬덤 커멘트 좋아요 수 업데이트
        return targetFandomCommentLikes.getId();
    }

    @Transactional
    public Long contentsCommentLike(Long contentsCommentId,Long memberId){
        List<ContentsCommentLikes> contentsCommentLikesList = contentsCommentLikesRepository.findAllByContentsCommentIdAndMemberId(contentsCommentId,memberId);

        if(contentsCommentLikesList.size()>1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)

        if(contentsCommentLikesList.isEmpty()){ // 기존에 좋아요를 누른 경우가 없는 경우

            Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            ContentsComment contentsComment = contentsCommentRepository.findById(contentsCommentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS_COMMENT));
            ContentsCommentLikes contentsCommentLikes =ContentsCommentLikes.builder()
                    .likeStatus(LikeStatus.Continue)
                    .contentsComment(contentsComment)
                    .member(member)
                    .build();
            contentsCommentLikesRepository.save(contentsCommentLikes);
            contentsComment.updateLikes(contentsComment.getLikes()+1);
            return contentsCommentLikes.getId();

        }
        else{//기존에 좋아요를 눌렀다가 취소하고 다시 누른 경우
            ContentsCommentLikes contentsCommentLikes = contentsCommentLikesList.get(0);
            contentsCommentLikes.changeLikeStatus(LikeStatus.Continue);
            ContentsComment contentsComment = contentsCommentRepository.findById(contentsCommentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_COMMENT));
            contentsComment.updateLikes(contentsComment.getLikes()+1);
            return contentsCommentLikes.getId();
        }
    }

    @Transactional
    public Long contentsCommentLikeCancel(Long contentsCommentId, Long memberId){
        List<ContentsCommentLikes> contentsCommentLikesList = contentsCommentLikesRepository.findAllByContentsCommentIdAndMemberId(contentsCommentId,memberId);
        if(contentsCommentLikesList.size() >1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)
        if(contentsCommentLikesList.isEmpty()){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS_COMMENT_LIKES);} //콘텐츠 코멘트 라이크 엔티티가 존재하지 않는 경우
        ContentsCommentLikes targetContentsCommentLikes = contentsCommentLikesList.get(0);
        targetContentsCommentLikes.changeLikeStatus(LikeStatus.Cancel); //더티 체킹 활용
        ContentsComment contentsComment= contentsCommentRepository.findById(contentsCommentId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_COMMENT));
        contentsComment.updateLikes(contentsComment.getLikes()-1); //콘텐츠  코멘트 좋아요 수 업데이트
        return targetContentsCommentLikes.getId();
    }

    @Transactional
    public Long communityCommentLike(Long communityCommentId,Long memberId){
        List<CommunityCommentLikes> communityCommentLikesList = communityCommentsLikesRepository.findAllByCommunityCommentIdAndMemberId(communityCommentId,memberId);

        if(communityCommentLikesList.size()>1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)

        if(communityCommentLikesList.isEmpty()){ // 기존에 좋아요를 누른 경우가 없는 경우

            Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            CommunityComment communityComment = communityCommentRepository.findById(communityCommentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY_COMMENT));
            CommunityCommentLikes communityCommentLikes =CommunityCommentLikes.builder()
                    .likeStatus(LikeStatus.Continue)
                    .communityComment(communityComment)
                    .member(member)
                    .build();
            communityCommentsLikesRepository.save(communityCommentLikes);
            communityComment.updateLikes(communityComment.getLikes()+1);
            return communityCommentLikes.getId();

        }
        else{//기존에 좋아요를 눌렀다가 취소하고 다시 누른 경우
            CommunityCommentLikes communityCommentLikes = communityCommentLikesList.get(0);
            communityCommentLikes.changeLikeStatus(LikeStatus.Continue);
            CommunityComment communityComment = communityCommentRepository.findById(communityCommentId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY_COMMENT));
            communityComment.updateLikes(communityComment.getLikes()+1);
            return communityCommentLikes.getId();
        }
    }

    @Transactional
    public Long communityCommentLikeCancel(Long communityCommentId, Long memberId){
        List<CommunityCommentLikes> communityCommentLikesList = communityCommentsLikesRepository.findAllByCommunityCommentIdAndMemberId(communityCommentId,memberId);
        if(communityCommentLikesList.size() >1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)
        if(communityCommentLikesList.isEmpty()){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY_COMMENT_LIKES);} //커뮤니티 코멘트 라이크 엔티티가 존재하지 않는 경우
        CommunityCommentLikes targetCommunityCommentLikes = communityCommentLikesList.get(0);
        targetCommunityCommentLikes.changeLikeStatus(LikeStatus.Cancel); //더티 체킹 활용
        CommunityComment communityComment =communityCommentRepository.findById(communityCommentId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY_COMMENT));
        communityComment.updateLikes(communityComment.getLikes()-1); // 팬덤 커멘트 좋아요 수 업데이트
        return targetCommunityCommentLikes.getId();
    }
}
