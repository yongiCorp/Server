package com.brandol.dto.request;

import com.brandol.validation.annotation.ExistBrand;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class MemberBrandRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class addMemberBrand {
        @Schema(description = "멤버 브랜드 리스트에 추가할 대상 브랜드의 ID")
        @JsonProperty(value = "brandId")
        @ExistBrand // 브랜드 아이기가 DB에 존재하는 아이디 인지 체킹
        private Long brandId;
    }
}
