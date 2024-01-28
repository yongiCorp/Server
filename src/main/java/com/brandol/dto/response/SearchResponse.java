package com.brandol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
//서치리스폰스필드만들구 클래스안에다가 dto넣기. 필드는오브젝트타입으로, 그안에 dto

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private Map<String,Object> searchbrandList = new HashMap<>();
    private Map<String,Object> searchuserList = new HashMap<>();
    private Map<String,Object> searchcontentsList = new HashMap<>();
    private Map<String,Object> searchavatarstorelist = new HashMap<>();//얘는 보이는데 안의 브랜드 네임 등이 안보임


    public static SearchResponse MainPage(Map<String,Object> searchbrandList,
                                          Map<String,Object> searchuserList,
                                          Map<String,Object> searchcontents,
                                          Map<String,Object> searchavatarstores
                                          ){
        return  SearchResponse.builder()
                .searchbrandList(searchbrandList)
                .searchuserList(searchuserList)
                .searchcontentsList(searchcontents)
                .searchavatarstorelist(searchavatarstores)
                .build();

    }
}
