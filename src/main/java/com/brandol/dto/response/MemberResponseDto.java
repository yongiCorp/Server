package com.brandol.dto.response;

import lombok.*;

import java.util.List;

public class MemberResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberMainDto{
        public List<MainBannersDto> mainBannersDtoList;
        public List<SubBannersDto> subBannersDtoList;
        public List<MemberBrandListDto> memberBrandListDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MainBannersDto{
        private Long brandId;
        private String brandBackgroundImage;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SubBannersDto{
        private Long contentId;
        private List<String> images;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberBrandListDto{
        private Long brandId;
        private String brandName;
        private String profileImage;
        private Long sequence;
    }
}
