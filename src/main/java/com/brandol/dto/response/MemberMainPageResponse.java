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
public class MemberMainPageResponse { //페이지1 메인 화면 Response Dto
    private Map<String,Object> mainBanners = new HashMap<>();
    private Map<String,Object> subBanners = new HashMap<>();
    private Map<String,Object> brandList = new HashMap<>();
}

