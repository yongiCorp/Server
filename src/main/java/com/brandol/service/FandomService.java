package com.brandol.service;

import com.brandol.converter.FandomConverter;
import com.brandol.domain.Fandom;
import com.brandol.domain.FandomImage;
import com.brandol.domain.Member;
import com.brandol.dto.response.FandomResponseDto;
import com.brandol.repository.FandomImageRepository;
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

}
