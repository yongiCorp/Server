package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
@ToString
public class MemberBrandService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final ContentsRepository contentsRepository;
    private final MemberBrandRepository memberBrandRepository;

    @Transactional
    public MemberMainPageResponse createMemberMainPage(Long memberId){

        if(memberRepository.findOneById(memberId)==null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER);}
        // 메인배너
        List<Brand> mainBannerBrands = new ArrayList<>();
        Brand brandol = brandRepository.findOneByName("brandol");
        if(brandol == null){ throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_BRANDOL_MAIN_BANNER);}
        mainBannerBrands.add(brandol);
        List<Brand> brands = brandRepository.findRecentBrandsExceptForOne("brandol",4);
        if(brands == null){ throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_MAIN_BANNER);}
        mainBannerBrands.addAll(brands);
        Map<String,Object> mainBanners = MainBanners.createMainBanners(mainBannerBrands);

        //서브배너
        List<Contents> subBannersContents = contentsRepository.findRecentBrands(10);
        if(subBannersContents == null){ throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_SUB_BANNER);}
        Map<String,Object> subBanners = SubBanners.createSubBanners(subBannersContents);

        // 브랜드 리스트
        List<MemberBrandList>memberBrandList = memberBrandRepository.findAllSubscribedByMemberId(memberId); // 현재 구독중인 브랜드의 리스트만 가져옴
        Map<String,Object> brandList = BrandList.createBrandList(memberBrandList);

        return MemberMainPageResponse.makeMainPage(mainBanners,subBanners,brandList);

    }

    @Transactional
    public MemberBrandList MemberBrandListStatusToUnsubscribed(Long memberId,Long brandId){

        List<MemberBrandList> searchResult = memberBrandRepository.findOneByMemberIdAndBrandId(memberId,brandId);
        int size = searchResult.size();
        if(size >1 || size ==0 ){
            if(size >1){throw  new ErrorHandler(ErrorStatus._DUPLICATE_DATABASE_ERROR);} //중복 조회 케이스
            throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER_BRAND_LIST); //조회 실패
        }
        MemberBrandList target = searchResult.get(0);
        target.changeMemberListStatus(MemberListStatus.UNSUBSCRIBED); //더티 체킹

        return target;
    }
}
