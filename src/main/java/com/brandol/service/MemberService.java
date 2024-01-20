package com.brandol.service;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.enums.MemberListStatus;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.repository.BrandRepository;
import com.brandol.repository.MemberBrandRepository;
import com.brandol.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    public Member findOneById(Long memberId){
        return memberRepository.findOneById(memberId);
    } //멤버 Id로 멤버를 찾오는 함수

    public Long addMemberBrandList(Long memberId, Long brandId){ //멤버가 멤버브랜드리스트에 브랜드를 추가 하는 함수

        Member member= memberRepository.findOneById(memberId);
        Brand brand = brandRepository.findByOneId(brandId);

        MemberBrandList memberBrandEntity = MemberBrandList.builder()
                .memberListStatus(MemberListStatus.SUBSCRIBED)
                .member(member)
                .brand(brand)
                .build();

        return memberBrandRepository.addMemberBrandList(memberBrandEntity);
    }

    public List<Brand> findAllBrandByMemberId(Long memberId){ // 멤버가 멤버브랜드리스트에 추가한 브랜드들의 리스트를 가져오는 함수
        return memberBrandRepository.findAllBrandByMemberId(memberId);
    }
}
