package com.brandol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandCommonHeaderResponse {
    private Map<String,Object> brandHeader = new HashMap<>(); //브랜드 페이지 헤더
    public static BrandCommonHeaderResponse makeBrandHeader(Map<String,Object>brandHeader){

        return BrandCommonHeaderResponse.builder()
                .brandHeader(brandHeader)
                .build();
    }
}
