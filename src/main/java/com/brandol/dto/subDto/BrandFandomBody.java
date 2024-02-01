package com.brandol.dto.subDto;

import com.brandol.domain.Fandom;
import com.brandol.domain.FandomImage;
import com.brandol.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandFandomBody {
    private List<Fandom> fandomList = new ArrayList<>();
    private Member adminMember;

    public static Map<String,Object> createFandomBody(List<Fandom>fandomCultureList,
                                                      List<Fandom>fandomNoticeList,
                                                      Map<Integer,Object> fandomCultureImages,
                                                      Map<Integer,Object> fandomNoticeImages,
                                                      Member adminMember){
        Map<String,Object> result = new LinkedHashMap<>();

        //팬덤컬처
        Map<String,Object> fandomCulture = new LinkedHashMap<>();
        int fandomCultureLen = fandomCultureList.size();
        for(int i=0;i<fandomCultureLen;i++){
            Map<String,Object> fandomCultureData = new LinkedHashMap<>();
            String key = "article" + i;
            fandomCultureData.put("writer-id",adminMember.getId());
            fandomCultureData.put("writer-name",adminMember.getName());
            fandomCultureData.put("writer-profile", adminMember.getAvatar());
            fandomCultureData.put("title",fandomCultureList.get(i).getTitle());
            fandomCultureData.put("content",fandomCultureList.get(i).getContent());
            fandomCultureData.put("images",fandomCultureImages.get(i));
            fandomCultureData.put("like-count",fandomCultureList.get(i).getLikes());
            fandomCultureData.put("comment-count",fandomCultureList.get(i).getComments());
            fandomCultureData.put("written-date",fandomCultureList.get(i).getCreatedAt());
            fandomCulture.put(key,fandomCultureData);
        }

        //팬덤 아나운스먼트
        Map<String,Object> fandomNotice = new LinkedHashMap<>();
        int fandomNoticeLen = fandomNoticeList.size();
        for(int i=0;i<fandomNoticeLen;i++){
            Map<String,Object>fandomNoticeData = new LinkedHashMap<>();
            String key = "notice"+i;
            fandomNoticeData.put("writer-id",adminMember.getId());
            fandomNoticeData.put("writer",adminMember.getName());
            fandomNoticeData.put("writer-profile",adminMember.getAvatar());
            fandomNoticeData.put("title",fandomNoticeList.get(i).getTitle());
            fandomNoticeData.put("content",fandomNoticeList.get(i).getContent());
            fandomNoticeData.put("images",fandomNoticeImages.get(i));
            fandomNoticeData.put("like-count",fandomNoticeList.get(i).getLikes());
            fandomNoticeData.put("comment-count",fandomNoticeList.get(i).getComments());
            fandomNoticeData.put("written-date",fandomNoticeList.get(i).getCreatedAt());
            fandomNotice.put(key,fandomNoticeData);
        }

        result.put("fandom-culture",fandomCulture); //팬덤컬처 탑재
        result.put("fandom-notice",fandomNotice); // 팬검 노티스 탑재

        return result;
    }
}
