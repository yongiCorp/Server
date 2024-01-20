package com.brandol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMemberBrandRequest { // 멤버브랜드 리스트 추가시 브랜드 id를 받는 DTO
    @JsonProperty(value = "brandId")
    private Long brandId;
}
