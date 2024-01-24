package com.brandol.service;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.response.MemberMainPageResponse;
import com.brandol.dto.subDto.BrandList;
import com.brandol.dto.subDto.MainBanners;
import com.brandol.dto.subDto.SubBanners;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@ToString
public class MemberBrandService {


    private final BrandRepository brandRepository;
    private final ContentsRepository contentsRepository;
    private final MemberBrandRepository memberBrandRepository;

    public MemberMainPageResponse createMemberMainPage(Long memberId){

        // 메인배너
        List<Brand> mainBannerBrands = new ArrayList<>();
        Brand brandol = brandRepository.findOneByName("brandol");
        if(brandol == null){ throw new RuntimeException("brandol brand 탐색 실패");}
        mainBannerBrands.add(brandol);
        List<Brand> brands = brandRepository.findRecentBrandsExceptForOne("brandol",4);
        if(brands == null){ throw new RuntimeException("메인 배너 탐색 실패");}
        mainBannerBrands.addAll(brands);
        Map<String,Object> mainBanners = MainBanners.createMainBanners(mainBannerBrands);

        //서브배너
        List<Contents> subBannersContents = contentsRepository.findRecentBrands(10);
        if(subBannersContents == null){ throw new RuntimeException("서브배너 탐색 실패");}
        Map<String,Object> subBanners = SubBanners.createSubBanners(subBannersContents);

        // 브랜드 리스트
        List<MemberBrandList>memberBrandList = memberBrandRepository.findAllSubscribedByMemberId(memberId); // 현재 구독중인 브랜드의 리스트만 가져옴
        Map<String,Object> brandList = BrandList.createBrandList(memberBrandList);

        return MemberMainPageResponse.makeMainPage(mainBanners,subBanners,brandList);

    }

    public MemberBrandList MemberBrandListStatusToUnsubscribed(Long memberId,Long brandId){

        List<MemberBrandList> searchResult = memberBrandRepository.findOneByMemberIdAndBrandId(memberId,brandId);
        int size = searchResult.size();
        if(size >1 || size ==0 ){
            throw  new RuntimeException("'멤버-브랜드-리스트'조회 실패");
        }
        MemberBrandList target = searchResult.get(0);
        target.changeMemberListStatus(MemberListStatus.UNSUBSCRIBED); //더티 체킹

        return target;

    }
}
