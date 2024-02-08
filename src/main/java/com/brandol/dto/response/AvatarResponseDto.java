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
}
