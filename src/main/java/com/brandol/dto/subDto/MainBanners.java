package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import lombok.*;

import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MainBanners {
    private List<Brand> brandList = new ArrayList<>();


    //MemberMainPageResponse 필드 종속
    public static Map<String,Object> createMainBanners(List<Brand>brandList){
        Map<String,Object> result = new LinkedHashMap<>(); // 리턴 변수 생성
        Integer arrayLen = brandList.size(); // 브랜드 리스트 len

        if(arrayLen < 1 ){ // 리스트에 값이 없는 경우:  Brand 테이블에서 값을 퍼오는데 실패한 경우
            throw new RuntimeException("브랜드 리스트가 비정상 입니다.");
        }


        for(int i =0 ; i< arrayLen; i++){
            String key = "main-banner" +i;
            Map<String,Object> bannerData = new LinkedHashMap<>();
            Brand target = brandList.get(i);
            bannerData.put("brand-id",target.getId());
            bannerData.put("banner-profile", target.getProfileImage());
            result.put(key,bannerData);
        }

        return result;

    }
}
