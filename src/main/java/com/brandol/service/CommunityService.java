package com.brandol.service;

import com.brandol.converter.CommunityConverter;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.CommunityImage;
import com.brandol.dto.response.CommunityResponseDto;
import com.brandol.repository.CommunityImageRepository;
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
}
