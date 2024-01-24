package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.mapping.MemberBrandList;
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
    public static Map<String,Object> createBrandList(List<MemberBrandList>brandList){ //유저가 리스트에 추가한 브랜드의 id,이름,프로필 URL을 리턴함.
        Map<String,Object> result = new LinkedHashMap<>();

        int arrayLen = brandList.size(); // 브랜드 리스트 len

        for(int i =0 ; i< arrayLen ; i++){
            String key = "brand" + i;
            Map<String,Object> brandData = new LinkedHashMap<>();
            MemberBrandList target = brandList.get(i);
            brandData.put("brand-id",target.getBrand().getId());
            brandData.put("brand-name",target.getBrand().getName());
            brandData.put("brand-image",target.getBrand().getProfileImage());
            brandData.put("brand-fan",target.getSequence());
            result.put(key,brandData);
        }

        return result;
    }
}
