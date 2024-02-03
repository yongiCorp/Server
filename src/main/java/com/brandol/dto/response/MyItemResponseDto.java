package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;

public class MyItemResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyItemDto {
        private Long myItemId; // 불필요하면 삭제 예정
        private Long itemId;
        private Long brandId;
        private String brandName;
        private String itemName;
        private boolean isWearing;
        private String part; // enum
        private String description;
        private String image;
        private int price;
        private LocalDateTime createdAt;
    }
}
