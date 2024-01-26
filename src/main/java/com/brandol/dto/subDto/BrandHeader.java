package com.brandol.dto.subDto;

import com.brandol.domain.Brand;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandHeader {
    private Brand brand;
    private BrandList brandList;
    private int recentFanCount;

    public static Map<String,Object> createBrandFandomHeader(Brand brand, MemberBrandList memberBrandList, int recentFanCount){
        Map<String,Object> result = new LinkedHashMap<>();

        // 브랜드 정보
        Map<String,Object> brandInfo = new LinkedHashMap<>();
        brandInfo.put("brand-id",brand.getId());
        brandInfo.put("brand-fan",recentFanCount);
        brandInfo.put("brand-name",brand.getName());
        brandInfo.put("brand-desc",brand.getDescription());
        brandInfo.put("brand-profile",brand.getProfileImage());
        brandInfo.put("brand-background",brand.getBackgroundImage());

        //유저 상태
        Map<String,Object> userStatus = new LinkedHashMap<>();

        // 브랜드리스트에서 status가 subscribe 인 경우
        if( memberBrandList!= null && memberBrandList.getMemberListStatus() == MemberListStatus.SUBSCRIBED){
            userStatus.put("is-fan",true);
            userStatus.put("join-date",memberBrandList.getCreatedAt()); //최초 가입일
            userStatus.put("fan-sequence",memberBrandList.getSequence()); //최초 가입시 가입 서열
        }
        else{ // 브랜드 리스트가 null +브랜드리스트에서 status가 unsubscribe 인경우
            userStatus.put("is-fan",false);
        }
        result.put("brand-info",brandInfo);
        result.put("user-status",userStatus);

        return result;
    }
}
