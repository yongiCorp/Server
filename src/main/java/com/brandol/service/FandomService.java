package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.FandomConverter;
import com.brandol.domain.Fandom;
import com.brandol.domain.FandomImage;
import com.brandol.domain.Member;
import com.brandol.domain.enums.LikeStatus;
import com.brandol.domain.mapping.FandomLikes;
import com.brandol.dto.response.FandomResponseDto;
import com.brandol.repository.FandomImageRepository;
import com.brandol.repository.FandomLikesRepository;
import com.brandol.repository.FandomRepository;
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
public class FandomService {

    private final FandomRepository fandomRepository;
    private final FandomImageRepository fandomImageRepository;
    private final MemberRepository memberRepository;
    private final FandomLikesRepository fandomLikesRepository;

    public List<FandomResponseDto.FandomDto> showAllFandomCultures(Integer pageNumber,Long brandId){

        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Fandom> fandomList = fandomRepository.getSomeRecentFandomCultures(brandId,pageable);

        List<FandomResponseDto.FandomDto> fandomDtoList = new ArrayList<>();
        for(int i =0;i<fandomList.size();i++){
            Fandom targetFandom = fandomList.get(i);
            List<FandomImage> fandomImages = fandomImageRepository.findFandomImages(targetFandom.getId());
            List<String> fandomImageUrlList = fandomImages.stream().map(FandomImage::getImage).collect(Collectors.toList());
            Member writer = targetFandom.getMember();
            FandomResponseDto.FandomDto dto = FandomConverter.toFandomDto(targetFandom,fandomImageUrlList,writer);
            fandomDtoList.add(dto);
        }
        return  fandomDtoList;
    }

    public List<FandomResponseDto.FandomDto> showAllFandomAnnouncement(Integer pageNumber,Long brandId){

        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Fandom> fandomList = fandomRepository.getSomeRecentFandomNotices(brandId,pageable);

        List<FandomResponseDto.FandomDto> fandomDtoList = new ArrayList<>();
        for(int i =0;i<fandomList.size();i++){
            Fandom targetFandom = fandomList.get(i);
            List<FandomImage> fandomImages = fandomImageRepository.findFandomImages(targetFandom.getId());
            List<String> fandomImageUrlList = fandomImages.stream().map(FandomImage::getImage).collect(Collectors.toList());
            Member writer = targetFandom.getMember();
            FandomResponseDto.FandomDto dto = FandomConverter.toFandomDto(targetFandom,fandomImageUrlList,writer);
            fandomDtoList.add(dto);
        }
        return  fandomDtoList;
    }

    public FandomResponseDto.FandomDto showOneFandomArticle(Long fandomId){
        Fandom fandom = fandomRepository.findById(fandomId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM));
        List<FandomImage> fandomImages = fandomImageRepository.findFandomImages(fandom.getId());
        List<String> fandomImageUrlList = fandomImages.stream().map(FandomImage::getImage).collect(Collectors.toList());
        Member member = fandom.getMember();
        return FandomConverter.toFandomDto(fandom,fandomImageUrlList,member);
    }

    @Transactional
    public Long fandomLike(Long fandomId,Long memberId){

        List<FandomLikes> fandomLikesList = fandomLikesRepository.findAllByFandomIdAndMemberId(fandomId,memberId);
        if(fandomLikesList.size() > 1){throw  new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)

        if(fandomLikesList.isEmpty()){ // 기존에 좋아요를 누른 경우가 없는 경우
            Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            Fandom fandom = fandomRepository.findById(fandomId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM));
            FandomLikes fandomLikes = FandomLikes.builder()
                    .likeStatus(LikeStatus.Continue)
                    .member(member)
                    .fandom(fandom)
                    .build();
            fandomLikesRepository.save(fandomLikes);
            fandom.updateLikes(fandom.getLikes()+1); // 팬덤 좋아요 수 업데이트
            return fandomLikes.getId();
        }
        else{ //기존에 좋아요를 눌렀다가 취소하고 다시 누른 경우
            FandomLikes fandomLikes = fandomLikesList.get(0);
            fandomLikes.changeLikeStatus(LikeStatus.Continue); // 더티 체킹 활용
            Fandom fandom = fandomRepository.findById(fandomId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM));
            fandom.updateLikes(fandom.getLikes()+1); // 팬덤 좋아요 수 업데이트
            return fandomLikes.getId();
        }
    }

    @Transactional
    public Long fandomLikeCancel(Long fandomId,Long memberId){

        List<FandomLikes> fandomLikesList = fandomLikesRepository.findAllByFandomIdAndMemberId(fandomId,memberId);
        if(fandomLikesList.size() > 1){throw  new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)
        if(fandomLikesList.isEmpty()){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM_LIKES);} // 팬덤라이크 엔티티가 존재하지 않는 경우
        FandomLikes tagetFandomLikes = fandomLikesList.get(0);
        tagetFandomLikes.changeLikeStatus(LikeStatus.Cancel); // 더티 체킹 활용
        Fandom fandom = fandomRepository.findById(fandomId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_FANDOM));
        fandom.updateLikes(fandom.getLikes()-1); // 팬덤 좋아요 수 업데이트
        return tagetFandomLikes.getId();
    }

}
