package com.brandol.dto.response;

import lombok.*;

import java.util.List;

public class ItemResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AvatarStoreBodyListDto {
        private List<ItemResponseDto.AvatarStoreBodyDto> AvatarStoreBodyDto;

    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AvatarStoreBodyDto {
        private Long itemId;
        private String itemsName;
        private String itemPart;
        private String brandName;
        private String itemImage;
        private String itemDescription;
        private int itemPrice;

    }
}
