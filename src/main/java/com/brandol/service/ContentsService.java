package com.brandol.service;

import com.brandol.converter.ContentsConverter;
import com.brandol.domain.Contents;
import com.brandol.domain.ContentsImage;
import com.brandol.domain.Member;
import com.brandol.dto.response.ContentsResponseDto;
import com.brandol.repository.ContentImageRepository;
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

    public List<ContentsResponseDto.ContentsDto> showAllContentsCardNews(Integer pageNumber,Long brandId){
        Pageable pageable = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<Contents> contentsList = contentsRepository.findRecentCardNews(brandId,pageable);

        List<ContentsResponseDto.ContentsDto> contentsDtoList = new ArrayList<>();
        for(int i =0; i< contentsList.size();i++){
            Contents targetContents = contentsList.get(i);
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(targetContents.getId());
            List<String> contentsUrlList = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            Member writer = memberRepository.findOneById(targetContents.getMember().getId());
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
            Member writer = memberRepository.findOneById(targetContents.getMember().getId());
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
            Member writer = memberRepository.findOneById(targetContents.getMember().getId());
            ContentsResponseDto.ContentsDto dto = ContentsConverter.toContentsDto(targetContents,contentsUrlList,writer);
            contentsDtoList.add(dto);
        }
        return  contentsDtoList;
    }
}
