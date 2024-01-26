package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final MemberBrandRepository memberBrandRepository;

    public Member findOneById(Long memberId){
        Member result = memberRepository.findOneById(memberId);
        if(result == null) {throw new RuntimeException("멤버 조회실패");}
        return result;
    } //멤버 Id로 멤버를 찾오는 함수


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
                throw new ErrorHandler(ErrorStatus._ALREADY_EXIST_MEMBERM_BRAND_LIST);
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
}
