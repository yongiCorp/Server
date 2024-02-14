package com.brandol.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MyItemRequestDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class wearItemsDto {
        @Schema(description = "구매한 아이템들 중 착용할 아이템의 id 리스트")
        //@NotNull
        private List<Long> wearingItemIdList; // 착용할 아이템 아이디 리스트
        private MultipartFile avatarImage; // 저장할 아바타 이미지
    }
}
