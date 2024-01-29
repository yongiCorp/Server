package com.brandol.dto.subDto;


import com.brandol.domain.Contents;



import java.util.LinkedHashMap;
import java.util.Map;

public class SearchContentsList {



    public static Map<String,Object> createsearchcontentsList
            (
             Contents searchContents,
             Long ContentsLikes,
             Long ContentsComments
             ){
        Map<String,Object> result = new LinkedHashMap<>();

        int arrayLen = 1;

        for(int i =0 ; i< arrayLen ; i++){

            Map<String,Object> searchcontentsData = new LinkedHashMap<>();
            searchcontentsData.put("contents-id",searchContents.getId());
            searchcontentsData.put("contents-title",searchContents.getTitle());
            searchcontentsData.put("contents-content",searchContents.getContent());
            searchcontentsData.put("contents-file",searchContents.getFile());
            searchcontentsData.put("contents-createdAt",searchContents.getCreatedAt());
            searchcontentsData.put("contents-numberofLikes", ContentsLikes);
            searchcontentsData.put("contents-numberofComments",ContentsComments);
            //더 추가필요(작성자명,작성자프로필,브랜드이름)
            result = searchcontentsData;
        }

        return result;
    }
}
