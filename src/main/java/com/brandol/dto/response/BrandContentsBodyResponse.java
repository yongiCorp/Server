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
public class BrandContentsBodyResponse {

    private Map<String,Object> brandContentsBody = new HashMap<>();
    public static BrandContentsBodyResponse makeBrandBody(Map<String,Object>brandContents){
        return BrandContentsBodyResponse.builder()
                .brandContentsBody(brandContents)
                .build();
    }

}
