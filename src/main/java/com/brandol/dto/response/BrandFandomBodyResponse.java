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
public class BrandFandomBodyResponse { // 브랜드 페이지 바디
    private Map<String,Object> brandFandomBody = new HashMap<>();
    public static BrandFandomBodyResponse makeBrandBody(Map<String,Object>brandFandom){
        return BrandFandomBodyResponse.builder()
                .brandFandomBody(brandFandom)
                .build();
    }
}
