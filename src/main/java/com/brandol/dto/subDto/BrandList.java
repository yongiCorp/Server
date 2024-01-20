package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandList {

    private List<Brand> brandList = new ArrayList<>();

    //MemberMainPageResponse 필드 종속
    public static Map<String,Object> createBrandList(List<Brand>brandList){ //유저가 리스트에 추가한 브랜드의 id,이름,프로필 URL을 리턴함.
        Map<String,Object> result = new LinkedHashMap<>();

        Integer arrayLen = brandList.size(); // 브랜드 리스트 len

        if(arrayLen < 1){
            throw new RuntimeException("브랜드 리스트가 비정상 입니다.");
        }

        for(int i =0 ; i< arrayLen ; i++){
            String key = "brand" + i;
            Map<String,Object> brandData = new LinkedHashMap<>();
            Brand target = brandList.get(i);
            brandData.put("brand-id",target.getId());
            brandData.put("brand-name",target.getName());
            brandData.put("brand-image",target.getProfileImage());
            result.put(key,brandData);
        }

        return result;
    }
}
