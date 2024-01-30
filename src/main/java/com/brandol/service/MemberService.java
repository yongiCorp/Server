package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.MemberConverter;
import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.ContentsImage;
import com.brandol.domain.Member;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.response.MemberResponseDto;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final MemberBrandRepository memberBrandRepository;
    private final ContentsRepository contentsRepository;
    private final ContentImageRepository contentImageRepository;

    @Transactional
    public Long addMemberBrandList(Long memberId, Long brandId){ //멤버가 멤버브랜드리스트에 브랜드를 추가 하는 함수

        Member member = memberRepository.findOneById(memberId);
        if(member == null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER);}

        Brand brand = brandRepository.findOneById(brandId);
        if(brand == null){
            throw new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND);}

        List<MemberBrandList> memberBrandLists = memberBrandRepository.findOneByMemberIdAndBrandId(memberId,brandId);
        int len = memberBrandLists.size();

        //기존에 구독했던 기록이 존재하는 경우
        if(len == 1){
            MemberBrandList memberBrandList=memberBrandLists.get(0);

            if(memberBrandList.getMemberListStatus() == MemberListStatus.UNSUBSCRIBED){ // 구독을 취소한 경우
            memberBrandList.changeMemberListStatus(MemberListStatus.SUBSCRIBED);
            return memberBrandList.getId();
            }
            else { //중복 구독을 신청한 경우
                throw new ErrorHandler(ErrorStatus._ALREADY_EXIST_MEMBER_BRAND_LIST);
            }
        }


        Long fanCount=1L;
        // 기존에 구독했던 적이 없는 경우

        //가장 마지막으로 구독했던 사람의 sequence 가져오기
        List<MemberBrandList> recentJoinedMemberBrandList = memberBrandRepository.getBrandJoinedFanCount(brandId, PageRequest.of(0,1));
        if(!recentJoinedMemberBrandList.isEmpty()){ fanCount = recentJoinedMemberBrandList.get(0).getSequence()+1;}

        MemberBrandList memberBrandEntity = MemberBrandList.builder()
                .memberListStatus(MemberListStatus.SUBSCRIBED)
                .member(member)
                .brand(brand)
                .sequence(fanCount)
                .build();


        memberBrandRepository.save(memberBrandEntity);
        return memberBrandEntity.getId();

    }

    public MemberResponseDto.MemberMainDto makeMemberMain(Long memberId){

        Member member = memberRepository.findOneById(memberId);
        if(member == null){throw new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER);}

        // 메인배너에 들어갈 브랜드들의 리스트
        Brand brandol = brandRepository.findOneByName("brandol");
        if(brandol == null){throw new ErrorHandler(ErrorStatus._CANNOT_LOAD_BRANDOL_MAIN_BANNER);}
        List<Brand> brandList = brandRepository.findRecentBrandsExceptForOne("brandol",4);
        brandList.add(0,brandol);

        //서브배너에 들어갈 콘텐츠 리스트
        List<Contents> contentsList = contentsRepository.findRecentBrands(10);

        //브랜드리스트에 들어갈 멤버-브랜드-리스트 타입의 리스트
        List<MemberBrandList> memberBrandLists = memberBrandRepository.findAllSubscribedByMemberId(memberId);

        List<MemberResponseDto.MainBannersDto> mainBannersDtoList = new ArrayList<>();
        for(int i=0; i<brandList.size();i++){
            MemberResponseDto.MainBannersDto dto = MemberConverter.toMainBannersDto(brandList.get(i));
            mainBannersDtoList.add(dto);
        }

        List<MemberResponseDto.SubBannersDto> subBannersDtoList = new ArrayList<>();
        for(int i=0; i<contentsList.size();i++){
            List<ContentsImage> contentsImages = contentImageRepository.findAllByContentsId(contentsList.get(i).getId());
            List<String>imageUrls = contentsImages.stream().map(ContentsImage::getImage).collect(Collectors.toList());
            MemberResponseDto.SubBannersDto dto = MemberConverter.toSubBannersDto(contentsList.get(i),imageUrls);
            subBannersDtoList.add(dto);
        }

        List<MemberResponseDto.MemberBrandListDto> memberBrandListDtoList = new ArrayList<>();
        for(int i=0; i<memberBrandLists.size();i++){
            MemberResponseDto.MemberBrandListDto dto = MemberConverter.toMemberBrandListDto(memberBrandLists.get(i));
            memberBrandListDtoList.add(dto);
        }

        return MemberConverter.toMemberMainDto(mainBannersDtoList,subBannersDtoList,memberBrandListDtoList);

    }

}
