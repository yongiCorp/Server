package com.brandol.repository;


import com.brandol.domain.Brand;
import com.brandol.domain.mapping.MemberBrandList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberBrandRepository {

    private final EntityManager em;

    public Long addMemberBrandList(MemberBrandList memberBrandList){ // 멤버브랜드리스트 엔티티를 저장하는 함수
        em.persist(memberBrandList);
        return memberBrandList.getId();
    }

    @Transactional(readOnly = true) //읽기 성능 향상 목적
    public List<Brand> findAllBrandByMemberId(Long memberId){ //멤버브랜드리스트 테이블에서 멤버 아이디를 기준으로 브랜드 목록을 가져오는 함수

        List<MemberBrandList> memberBrandLists = em.createQuery("select mbl from MemberBrandList mbl where mbl.member.id =: memberId", MemberBrandList.class)
                .setParameter("memberId" , memberId).getResultList();

        List<Brand> result = new ArrayList<>();
        int len = memberBrandLists.size();

        for(int i=0 ; i< len; i++){
            result.add(memberBrandLists.get(i).getBrand());
        }

        return result;
    }
}
