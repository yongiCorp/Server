package com.brandol.converter;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.ContentsImage;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.request.BrandRequestDto;
import com.brandol.dto.response.BrandResponseDto;
import com.brandol.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberConverter {

    public static MemberResponseDto.MainBannersDto toMainBannersDto(Brand brand){
        return MemberResponseDto.MainBannersDto.builder()
                .brandId(brand.getId())
                .brandBackgroundImage(brand.getBackgroundImage())
                .build();
    }

    public static MemberResponseDto.SubBannersDto toSubBannersDto(Contents contents, List<String>imageUrlList){
        return MemberResponseDto.SubBannersDto.builder()
                .contentId(contents.getId())
                .images(imageUrlList)
                .build();
    }
    public static MemberResponseDto.MemberBrandListDto toMemberBrandListDto(MemberBrandList memberBrandList){
        return MemberResponseDto.MemberBrandListDto.builder()
                .brandId(memberBrandList.getBrand().getId())
                .brandName(memberBrandList.getBrand().getName())
                .profileImage(memberBrandList.getBrand().getProfileImage())
                .sequence(memberBrandList.getSequence())
                .build();
    }

    public static MemberResponseDto.MemberMainDto toMemberMainDto(
            List<MemberResponseDto.MainBannersDto> mainBannersDtoList,
            List<MemberResponseDto.SubBannersDto> subBannersDtoList,
            List<MemberResponseDto.MemberBrandListDto> memberBrandListDtoList){
        return MemberResponseDto.MemberMainDto.builder()
                .mainBannersDtoList(mainBannersDtoList)
                .subBannersDtoList(subBannersDtoList)
                .memberBrandListDtoList(memberBrandListDtoList)
                .build();
    }
}