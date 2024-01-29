package com.brandol.dto.request;

import lombok.*;

public class MemberBrandRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class addMemberBrand {
        private Long brandId;
    }
}
