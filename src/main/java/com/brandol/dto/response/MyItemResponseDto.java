package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

public class MyItemResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyItemDto {
        private Long myItemId;
        private Long itemId;
        private Long brandId;
        private String brandName;
        private String itemName;
        private boolean isWearing;
        private String part;
        private String description;
        private String image;
        private String wearingImage;
        private int price;
        private LocalDateTime createdAt;
    }
}
