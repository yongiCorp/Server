package com.brandol.service;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final MemberBrandRepository memberBrandRepository;

    @Transactional(readOnly = true)
    public Member findOneById(Long memberId){
        Member result = memberRepository.findOneById(memberId);
        if(result == null) {throw new RuntimeException("멤버 조회실패");}
        return result;
    } //멤버 Id로 멤버를 찾오는 함수


    public Long addMemberBrandList(Long memberId, Long brandId){ //멤버가 멤버브랜드리스트에 브랜드를 추가 하는 함수

        Member member = memberRepository.findOneById(memberId);
        if(member == null){throw new RuntimeException("멤버 조회 실패");}

        Brand brand = brandRepository.findOneById(brandId);
        if(brand == null){throw new RuntimeException("브랜드 조회 실패");}


        Long fanCount=1L;

        List<MemberBrandList> memberBrandLists = memberBrandRepository.getBrandJoinedFanCount(brandId, PageRequest.of(0,1));
        if(memberBrandLists.size() != 0){ fanCount = memberBrandLists.get(0).getSequence()+1;}

        MemberBrandList memberBrandEntity = MemberBrandList.builder()
                .memberListStatus(MemberListStatus.SUBSCRIBED)
                .member(member)
                .brand(brand)
                .sequence(fanCount)
                .build();


        memberBrandRepository.save(memberBrandEntity);
        return memberBrandEntity.getId();
    }


    @Transactional(readOnly = true)
    public List<Brand> findAllBrandByMemberId(Long memberId){ // 멤버가 멤버브랜드리스트에 추가한 브랜드들의 리스트를 가져오는 함수
        //return JPQLMemberBrandRepository.findAllBrandByMemberId(memberId);
        return memberBrandRepository.findAllBrandByMemberId(memberId);
    }
}
