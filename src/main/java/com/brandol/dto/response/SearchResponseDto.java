package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


public class SearchResponseDto {


    //검색 메인 페이지
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchMainAllDto {
        private List<SearchMainBrandDto> searchMainBrandDto;
        private List<SearchMainUserDto> searchMainUserDto;
        private List<SearchMainContentsDto> searchMainContentsDto;
        private List<SearchMainAvatarStoreDto> searchMainAvatarStoreDto;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchMainBrandDto {
        private Long brandId;
        private String brandName;
        private String brandProfileImage;
        private String brandDescription;

    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchMainUserDto {
        private Long userId;
        private String userName;
        private String userAvatar;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchMainContentsDto {
        private Long contentsId;
        private String contentsTitle;
        private String content;
        private List<String> images;
        private int likeCount;
        private int commentCount;
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private LocalDateTime createdDate;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchMainAvatarStoreDto {
        private Long itemId;
        private String itemsName;
        private String itemPart;
        private String brandName;
        private String itemImage;
        private String itemDescription;
        private int itemPrice;

    }

    //검색 더보기 페이지 - 브랜드

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailBrandAllDto {
        private List<SearchDetailBrandDto> searchDetailBrandDto;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailBrandDto {
        private Long brandId;
        private String brandName;
        private String brandProfileImage;
        private String brandBackgroundImage;
        private String brandDescription;
        private Integer brandFans;

    }

    //검색 더보기 페이지 - 유저
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailUserAllDto {
        private List<SearchDetailUserDto> searchDetailUserDto;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailUserDto {
        private Long userId;
        private String userName;
        private String userAvatar;

    }

    //검색 더보기 페이지 - 컨텐츠
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailContentsAllDto {
        private List<SearchDetailContentsDto> searchDetailContentsDto;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailContentsDto {
        private Long contentsId;
        private String contentsTitle;
        private String content;
        private List<String> images;
        private int likeCount;
        private int commentCount;
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private LocalDateTime createdDate;

    }


    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailAvatarStoreHeaderDto {
        private Long memberId;
        private String memberAvatar;
        private Integer memberPoints;//타입

    }


    //검색 더보기 페이지 - 아바타 스토어 바디
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Search_Detail_AvatarStore_Body_All_Dto {
        private List<SearchDetailAvatarStoreBodyDto> searchDetailAvatarStoreBodyDto;

    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchDetailAvatarStoreBodyDto {
        private Long itemId;
        private String itemsName;
        private String itemPart;
        private String brandName;
        private String itemImage;
        private String itemDescription;
        private int itemPrice;

    }

}

