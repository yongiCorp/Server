package com.brandol.repository;


import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){ // 멤버 엔티티 저장함수
        em.persist(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public Member findOneById(Long id){ //멤버 아이디로 멤버 엔티티 조회 함수
        List<Member> members = em.createQuery("select m from Member m where m.id =: id", Member.class)
                .setParameter("id",id).getResultList();
        int len = members.size();

        if( len == 0|| len >1){
            throw new RuntimeException("멤버 조회 중 문제가 발생했습니다.");
        }
        return members.get(0);
    }

}
