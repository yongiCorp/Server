package com.brandol.dto.subDto;


import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Member;


import java.util.LinkedHashMap;
import java.util.Map;

public class SearchMainContentsList {



    public static Map<String,Object> createsearchcontentsList
            (
             Contents searchContents,
             Long ContentsLikes,
             Long ContentsComments,
             Brand searchBrand,
             Member searchMember

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
            searchcontentsData.put("contents-brandName",searchBrand.getName());
            searchcontentsData.put("contents-writerName",searchMember.getName());
            searchcontentsData.put("contents-writerProfile",searchMember.getAvatar());
            result = searchcontentsData;
        }

        return result;
    }
}
