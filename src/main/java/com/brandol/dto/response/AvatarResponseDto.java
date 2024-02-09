package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class AvatarResponseDto {

    // 다른 회원의 브랜드 리스트 조회
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OtherMemberBrandListDto{
        private Long brandId;
        private String brandName;
        private String description;
        private String profileImage;
        private Long sequence;
    }

    // 다른 아바타가 착용중인 아이템 조회
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberAvatarItemDto {
        private Long myItemId;
        private Long itemId;
        private Long brandId;
        private String brandName;
        private String itemName;
        private boolean isWearing;
        private String part;
        private String description;
        private String image;
        private int price;
        private LocalDateTime createdAt;
    }

    // 다른 회원이 작성한 글 조회
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OtherMemberCommunityDto {
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private String articleType;
        private Long id;
        private String title;
        private String content;
        private List<String> images;
        private int likeCount;
        private int commentCount;
        private LocalDateTime writtenDate;
    }
}
