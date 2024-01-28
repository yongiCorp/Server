package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.mapping.ContentsComment;
import com.brandol.domain.mapping.ContentsLikes;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.repository.SearchContentsRepository;
import com.brandol.service.SearchService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchContentsList {
    private List<Contents> searchcontentsList = new ArrayList<>();


    public static Map<String,Object> createsearchcontentsList
            (
             Contents searchContents,
             Long ContentsLikes
             //List<ContentsComment>search_contents_comments_List
             ){
        Map<String,Object> result = new LinkedHashMap<>();

        int arrayLen = 1;

        for(int i =0 ; i< arrayLen ; i++){
            String key = "searchcontents" + i;
            Map<String,Object> searchcontentsData = new LinkedHashMap<>();
            //ContentsComment target_c = search_contents_comments_List.get(i);
            //ContentsLikes target_l = search_contents_likes_List.get(i);
            searchcontentsData.put("contents-title",searchContents.getTitle());
            searchcontentsData.put("contents-content",searchContents.getContent());
            searchcontentsData.put("contents-file",searchContents.getFile());
            searchcontentsData.put("contents-id",searchContents.getId());
            searchcontentsData.put("contents-createdAt",searchContents.getCreatedAt());
            searchcontentsData.put("contents-numberofLikes", ContentsLikes);
            searchcontentsData.put("contents-numberofComments",searchContents.getCreatedAt());
            //더 추가필요(좋아요스,댓글수,작성자명,작성자프로필)
            result.put(key,searchcontentsData);
        }

        return result;
    }
}
