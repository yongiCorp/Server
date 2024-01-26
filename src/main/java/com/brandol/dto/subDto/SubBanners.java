package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.ContentsImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubBanners {

    private List<Contents> contentsList = new ArrayList<>();

    //MemberMainPageResponse 필드 종속
    public static Map<String,Object> createSubBanners(List<Contents> contentsEventList){
        Map<String,Object> result = new LinkedHashMap<>(); // 리턴 변수 생성
        Integer arrayLen = contentsEventList.size(); // 브랜드콘텐츠 리스트 len

        if(arrayLen < 1 ){ // 리스트에 값이 없는 경우:  BrandEvent 테이블에서 값을 퍼오는데 실패한 경우
            throw new RuntimeException("브랜드 이벤트 리스트가 비정상 입니다.");
        }

        for(int i =0 ; i< arrayLen; i++){
            String key = "sub-banner" +i;
            Map<String,Object> bannerData = new LinkedHashMap<>();
            Contents target = contentsEventList.get(i);
            bannerData.put("contents-id",target.getId());
            bannerData.put("banner-img", target.getFile());
            result.put(key,bannerData);
        }

        return result;
    }

}
