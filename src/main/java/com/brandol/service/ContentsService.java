package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.ContentsConverter;
import com.brandol.domain.Contents;
import com.brandol.domain.ContentsImage;
import com.brandol.domain.Member;
import com.brandol.domain.enums.LikeStatus;
import com.brandol.domain.mapping.ContentsLikes;
import com.brandol.dto.response.ContentsResponseDto;
import com.brandol.dto.response.FandomResponseDto;
import com.brandol.repository.ContentImageRepository;
import com.brandol.repository.ContentsLikesRepository;
import com.brandol.repository.ContentsRepository;
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
public class ContentsService {
    private final ContentsRepository contentsRepository;
    private final ContentImageRepository contentImageRepository;
    private final MemberRepository memberRepository;
    private final ContentsLikesRepository contentsLikesRepository;

    public List<ContentsResponseDto.ContentsDto> showAllContentsCardNews(Integer pageNumber,Long brandId){
        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Contents> contentsList = contentsRepository.findRecentCardNews(brandId,pageable);

        List<ContentsResponseDto.ContentsDto> contentsDtoList = new ArrayList<>();
        for(int i =0; i< contentsList.size();i++){
            Contents targetContents = contentsList.get(i);
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(targetContents.getId());
            List<String> contentsUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            Member writer = targetContents.getMember();
            ContentsResponseDto.ContentsDto dto = ContentsConverter.toContentsDto(targetContents,contentsUrlList,writer);
            contentsDtoList.add(dto);
        }
        return  contentsDtoList;
    }

    public List<ContentsResponseDto.ContentsDto> showAllContentsEvents(Integer pageNumber,Long brandId){
        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Contents> contentsList = contentsRepository.findRecentEvents(brandId,pageable);

        List<ContentsResponseDto.ContentsDto> contentsDtoList = new ArrayList<>();
        for(int i =0; i< contentsList.size();i++){
            Contents targetContents = contentsList.get(i);
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(targetContents.getId());
            List<String> contentsUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            Member writer = targetContents.getMember();
            ContentsResponseDto.ContentsDto dto = ContentsConverter.toContentsDto(targetContents,contentsUrlList,writer);
            contentsDtoList.add(dto);
        }
        return  contentsDtoList;
    }

    public List<ContentsResponseDto.ContentsDto> showAllContentsVideos(Integer pageNumber,Long brandId){
        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Contents> contentsList = contentsRepository.findRecentVideos(brandId,pageable);

        List<ContentsResponseDto.ContentsDto> contentsDtoList = new ArrayList<>();
        for(int i =0; i< contentsList.size();i++){
            Contents targetContents = contentsList.get(i);
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(targetContents.getId());
            List<String> contentsUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            Member writer = targetContents.getMember();
            ContentsResponseDto.ContentsDto dto = ContentsConverter.toContentsDto(targetContents,contentsUrlList,writer);
            contentsDtoList.add(dto);
        }
        return  contentsDtoList;
    }

    public ContentsResponseDto.ContentsDto showOneContentsArticle(Long contentsId){

        Contents contents = contentsRepository.findById(contentsId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS));
        List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(contents.getId());
        List<String> contentsImageUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
        Member member = contents.getMember();

        return ContentsConverter.toContentsDto(contents,contentsImageUrlList,member);
    }

    @Transactional
    public Long contentsLike(Long contentsId,Long memberId){ // 기존에 좋아요를 누른 경우가 없는 경우
        List<ContentsLikes> contentsLikesList = contentsLikesRepository.findAllByContentsIdAndMemberId(contentsId,memberId);
        if(contentsLikesList.size() > 1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)

        if(contentsLikesList.isEmpty()){
            Member member = memberRepository.findById(memberId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
            Contents contents = contentsRepository.findById(contentsId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS));

            ContentsLikes contentsLikes = ContentsLikes.builder()
                    .likeStatus(LikeStatus.Continue)
                    .member(member)
                    .contents(contents)
                    .build();
            contentsLikesRepository.save(contentsLikes);
            contents.updateLikes(contents.getLikes()+1);
            return contentsLikes.getId();
        }
        else{ //기존에 좋아요를 눌렀다가 취소하고 다시 누른 경우
            ContentsLikes contentsLikes = contentsLikesList.get(0);
            contentsLikes.changeLikeStatus(LikeStatus.Continue);
            Contents contents = contentsRepository.findById(contentsId).orElseThrow(()-> new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS));
            contents.updateLikes(contents.getLikes()+1);
            System.out.println("contents = " + contents.getLikes());
            return contentsLikes.getId();
        }
    }

    @Transactional
    public Long contentsLikeCancel(Long contentsId,Long memberId){

        List<ContentsLikes> contentsLikesList = contentsLikesRepository.findAllByContentsIdAndMemberId(contentsId,memberId);
        if(contentsLikesList.size()>1){throw new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} // DB 예외 처리(중복 좋아요 조회가 발생한 경우)
        if(contentsLikesList.isEmpty()){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS_LIKES);} // 콘텐츠 라이크 엔티티가 존재하지 않는 경우
        ContentsLikes targetContentsLikes = contentsLikesList.get(0);
        targetContentsLikes.changeLikeStatus(LikeStatus.Cancel);
        Contents contents = contentsRepository.findById(contentsId).orElseThrow(()->new ErrorHandler(ErrorStatus._CANNOT_LOAD_CONTENTS));
        contents.updateLikes(contents.getLikes()-1);
        return targetContentsLikes.getId();
    }
}
