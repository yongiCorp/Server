package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.CommunityConverter;
import com.brandol.domain.Contents;
import com.brandol.domain.Member;
import com.brandol.domain.enums.LikeStatus;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityImage;
import com.brandol.domain.mapping.CommunityLikes;
import com.brandol.domain.mapping.ContentsLikes;
import com.brandol.dto.response.CommunityResponseDto;
import com.brandol.repository.CommunityImageRepository;
import com.brandol.repository.CommunityLikesRepository;
import com.brandol.repository.CommunityRepository;
import com.brandol.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;
    private final MemberRepository memberRepository;
    private final CommunityLikesRepository communityLikesRepository;

    public List<CommunityResponseDto.CommunityDto> showAllCommunityAll(Integer pageNumber,Long brandId){

        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Community> communityList =communityRepository.findRecentFreeBoard(brandId,pageable);

        List<CommunityResponseDto.CommunityDto> communityDtoList = new ArrayList<>();
        for(int i= 0;i<communityList.size();i++){
            Community targetCommunity = communityList.get(i);
            List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(targetCommunity.getId());
            List<String>communityUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
            Member writer = targetCommunity.getMember();
            CommunityResponseDto.CommunityDto dto = CommunityConverter.toContentsDto(targetCommunity,communityUrlList,writer);
            communityDtoList.add(dto);
        }
        return communityDtoList;
    }

    public List<CommunityResponseDto.CommunityDto> showAllCommunityFeedBack(Integer pageNumber,Long brandId){

        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Community> communityList =communityRepository.findRecentFeedBackBoard(brandId,pageable);

        List<CommunityResponseDto.CommunityDto> communityDtoList = new ArrayList<>();
        for(int i= 0;i<communityList.size();i++){
            Community targetCommunity = communityList.get(i);
            List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(targetCommunity.getId());
            List<String>communityUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
            Member writer = targetCommunity.getMember();
            CommunityResponseDto.CommunityDto dto = CommunityConverter.toContentsDto(targetCommunity,communityUrlList,writer);
            communityDtoList.add(dto);
        }
        return communityDtoList;
    }

    public CommunityResponseDto.CommunityDto showOneCommunityArticle(Long communityId){

        Community community = communityRepository.findById(communityId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY));
        List<CommunityImage> communityImages = communityImageRepository.findAllByCommunityId(community.getId());
        List<String>communityImageUrlList = communityImages.stream().map(CommunityImage::getImage).collect(Collectors.toList());
        Member member = community.getMember();

        return CommunityConverter.toContentsDto(community,communityImageUrlList,member);
    }

    @Transactional
    public Long communityLike(Long communityId,Long memberId){ // 기존에 좋아요를 누른 경우가 없는 경우
        List<CommunityLikes> communityLikesList = communityLikesRepository.findAllByCommunityIdAndMemberId(communityId,memberId);
        if(communityLikesList.size() > 1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)

        if(communityLikesList.isEmpty()){
            Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            Community community = communityRepository.findById(communityId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY));

            CommunityLikes contentsLikes = CommunityLikes.builder()
                    .likeStatus(LikeStatus.Continue)
                    .member(member)
                    .community(community)
                    .build();
            communityLikesRepository.save(contentsLikes);
            community.updateLikes(community.getLikes()+1);
            return contentsLikes.getId();
        }
        else{ //기존에 좋아요를 눌렀다가 취소하고 다시 누른 경우
            CommunityLikes communityLikes = communityLikesList.get(0);
            communityLikes.changeLikeStatus(LikeStatus.Continue);
            Community community = communityRepository.findById(communityId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY));
            community.updateLikes(community.getLikes()+1);
            return communityLikes.getId();
        }
    }

    @Transactional
    public Long communityLikeCancel(Long communityId,Long memberId){
        List<CommunityLikes> communityLikesList = communityLikesRepository.findAllByCommunityIdAndMemberId(communityId,memberId);
        if(communityLikesList.size()>1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)
        if(communityLikesList.isEmpty()){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY_LIKES);} // 콘텐츠 라이크 엔티티가 존재하지 않는 경우
        CommunityLikes targetCommunityLikes = communityLikesList.get(0);
        targetCommunityLikes.changeLikeStatus(LikeStatus.Cancel);
        Community community = communityRepository.findById(communityId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_COMMUNITY));
        community.updateLikes(community.getLikes()-1);
        return targetCommunityLikes.getId();
    }
}
