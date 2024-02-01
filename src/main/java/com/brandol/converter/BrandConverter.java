package com.brandol.converter;

import com.brandol.domain.Brand;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.request.BrandRequestDto;
import com.brandol.dto.response.BrandResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrandConverter {
    public static BrandResponseDto.BrandPreviewDto toBrandPreviewDto(Brand brand, int fans) {
        return BrandResponseDto.BrandPreviewDto.builder()
                .brand_id(brand.getId())
                .brand_name(brand.getName())
                .brand_description(brand.getDescription())
                .brand_fan(fans)
                .brand_profile(brand.getProfileImage())
                .brand_background(brand.getBackgroundImage())
                .build();
    }
    public static BrandResponseDto.BrandUserStatus toUserStatusFromUser(MemberBrandList memberBrandList){
        if( memberBrandList!= null && memberBrandList.getMemberListStatus() == MemberListStatus.SUBSCRIBED) {
            return BrandResponseDto.BrandUserStatus.builder()
                    .isFan(true)
                    .join_date(memberBrandList.getCreatedAt().toLocalDate())
                    .fan_sequence(memberBrandList.getSequence())
                    .build();
        }
        return BrandResponseDto.BrandUserStatus.builder()
                .isFan(false)
                .build();
    }
    public static BrandResponseDto.BrandHeaderDto toBrandHeaderDto(
            BrandResponseDto.BrandPreviewDto brandPreviewDto,
            BrandResponseDto.BrandUserStatus brandUserStatus) {
        return BrandResponseDto.BrandHeaderDto.builder()
                .brandPreviewDto(brandPreviewDto)
                .brandUserStatus(brandUserStatus)
                .build();
    }
}
