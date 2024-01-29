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

    private Map<String,Object> BrandContentBody = new HashMap<>();

    public static BrandContentsBodyResponse makeBrandContentBody(Map<String,Object>brandContent){
        return BrandContentsBodyResponse.builder()
                .BrandContentBody(brandContent)
                .build();
    }
}
