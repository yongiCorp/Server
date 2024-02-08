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
}
