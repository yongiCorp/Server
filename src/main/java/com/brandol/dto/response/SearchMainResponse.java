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
public class SearchMainResponse {
    private Map<String,Object> searchbrandList = new HashMap<>();
    private Map<String,Object> searchuserList = new HashMap<>();
    private Map<String,Object> searchcontentsList = new HashMap<>();
    private Map<String,Object> searchavatarstorelist = new HashMap<>();//얘는 보이는데 안의 브랜드 네임 등이 안보임


    public static SearchMainResponse mainPage(Map<String,Object> searchbrandList,
                                              Map<String,Object> searchuserList,
                                              Map<String,Object> searchcontents,
                                              Map<String,Object> searchavatarstores
                                          ){
        return  SearchMainResponse.builder()
                .searchbrandList(searchbrandList)
                .searchuserList(searchuserList)
                .searchcontentsList(searchcontents)
                .searchavatarstorelist(searchavatarstores)
                .build();

    }
}
