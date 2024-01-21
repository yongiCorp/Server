package com.brandol.service;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
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

        List<Brand> mainBannerBrands = new ArrayList<>();  // 메인배너
        //Brand brandol = jpqlBrandRepository.findBrandByName("brandol");
        Brand brandol = brandRepository.findOneByName("brandol");
        if(brandol == null){ throw new RuntimeException("brandol brand 탐색 실패");}
        mainBannerBrands.add(brandol);
        //List<Brand> brands = jpqlBrandRepository.findRecentBrandsExceptForBrandol(4);
        List<Brand> brands = brandRepository.findRecentBrandsExceptForOne("brandol",4);
        if(brands == null){ throw new RuntimeException("메인 배너 탐색 실패");}
        mainBannerBrands.addAll(brands);
        Map<String,Object> mainBanners = MainBanners.createMainBanners(mainBannerBrands);

        //List<Contents> subBannersCotents = jpqlContentsRepository.findRecentEvents(10); //서브배너
        List<Contents> subBannersCotents = contentsRepository.findRecentBrands(10);
        if(subBannersCotents == null){ throw new RuntimeException("서브배너 탐색 실패");}
        Map<String,Object> subBanners = SubBanners.createSubBanners(subBannersCotents);

        //List<Brand>memberBrandList = jpqlMemberBrandRepository.findAllBrandByMemberId(memberId); // 브랜드 리스트
        List<Brand>memberBrandList = memberBrandRepository.findAllBrandByMemberId(memberId);
        Map<String,Object> brandList = BrandList.createBrandList(memberBrandList);

        return MemberMainPageResponse.makeMainPage(mainBanners,subBanners,brandList);

    }

}
