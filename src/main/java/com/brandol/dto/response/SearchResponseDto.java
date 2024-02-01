package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


public class SearchResponseDto {



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

}
