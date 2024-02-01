package com.brandol.service;

import com.brandol.aws.AmazonS3Manager;
import com.brandol.converter.SearchConverter;
import com.brandol.domain.*;
import com.brandol.dto.response.SearchResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ToString
public class SearchService {

    private final AmazonS3Manager s3Manager;

    private final BrandRepository brandRepository;
    private final MemberRepository memberRepository;
    private final ContentsRepository contentsRepository;
    private final ItemsRepository itemsRepository;
    private final ContentImageRepository contentImageRepository;


    @Transactional
    public SearchResponseDto.SearchMainAllDto makeSearchMainPage(){



        // 브랜드 리스트
        List<Brand> searchmainbrandList = brandRepository.findThreeByRandom();

        // 멤버 리스트
        List<Member> searchmainuserList = memberRepository.findThreeByRandom();

        // 컨텐츠 리스트
        List<Contents> searchmaincontentList = contentsRepository.findThreeByRandom();


        // 아바타스토어 리스트
        List<Items> searchmainitemList = itemsRepository.findThreeByRandom();





        List<SearchResponseDto.SearchMainBrandDto> searchMainBrandDtoList = new ArrayList<>();
        for(int i=0; i< searchmainbrandList.size();i++){
            SearchResponseDto.SearchMainBrandDto dto = SearchConverter.toSearchMainBrandDto(searchmainbrandList.get(i));
            searchMainBrandDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainUserDto> searchMainUserDtoList = new ArrayList<>();
        for(int i=0; i<searchmainuserList.size();i++){
            SearchResponseDto.SearchMainUserDto dto = SearchConverter.toSearchMainUserDto(searchmainuserList.get(i));
            searchMainUserDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainContentsDto> searchMainContentsDtoList = new ArrayList<>();
        for(int i=0; i<searchmaincontentList.size();i++){
            List<ContentsImage> searchcontentsImages = contentImageRepository.findAllByContentsId(searchmaincontentList.get(i).getId());
            List<String> searchcontentsImageUrlList = searchcontentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            SearchResponseDto.SearchMainContentsDto dto = SearchConverter.toSearchMainContentsDto(searchmaincontentList.get(i),searchcontentsImageUrlList);
            searchMainContentsDtoList.add(dto);
        }

        List<SearchResponseDto.SearchMainAvatarStoreDto> searchMainAvatarStoreDtoList = new ArrayList<>();
        for(int i=0; i<searchmainitemList.size();i++){
            SearchResponseDto.SearchMainAvatarStoreDto dto = SearchConverter.toSearchMainAvatarStoreDto(searchmainitemList.get(i));
            searchMainAvatarStoreDtoList.add(dto);
        }






        return SearchConverter.tosearchMainAllDto(searchMainBrandDtoList,searchMainUserDtoList,searchMainContentsDtoList,searchMainAvatarStoreDtoList);


    }


}
