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
     /*
    createMainBanners의 리턴형 =>
    main-banner0 : {이미지0 URL},
    main-banner1 : {이미지1 URL},
    main-banner2 : {이미지2 URL}
     */
    public static Map<String,Object> createMainBanners(List<Brand>brandList){
        Map<String,Object> result = new LinkedHashMap<>(); // 리턴 변수 생성
        Integer arrayLen = brandList.size(); // 브랜드 리스트 len

        if(arrayLen < 1 ){ // 리스트에 값이 없는 경우:  Brand 테이블에서 값을 퍼오는데 실패한 경우
            throw new RuntimeException("브랜드 리스트가 비정상 입니다.");
        }

        for(int i =0 ; i< arrayLen; i++){ //리턴 변수 생성 영역
            String profileImage = brandList.get(i).getProfileImage();
            result.put("main-banner"+i,profileImage);
            System.out.println("profileImage = " + profileImage);
        }


        return result;

    }
}
