package com.brandol.dto.request;

import com.brandol.validation.annotation.ExistBrand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddMemberBrandRequest { // 멤버브랜드 리스트 추가시 브랜드 id를 받는 DTO
    @JsonProperty(value = "brandId")
    @ExistBrand // 브랜드 아이기가 DB에 존재하는 아이디 인지 체킹
    private Long brandId;
}
