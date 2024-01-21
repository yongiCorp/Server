package com.brandol.repository;

import com.brandol.domain.Contents;
import com.brandol.domain.enums.ContentsType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ContentsRepository {

    private final EntityManager em;

    @Transactional(readOnly = true) //읽기 성능 향상 목적
    public List<Contents> findRecentEvents(int cnt){ //최신 등록 Brand들 중 cnt개의 brand List를 리턴하는 함수

        List<Contents> contents = em.createQuery("select c from Contents c where c.contentsType =: contentsType order by c.createdAt DESC",Contents.class)
                .setParameter("contentsType", ContentsType.EVENTS)
                .setMaxResults(cnt).getResultList();

        int len = contents.size();
        if(len == 0){
            throw new RuntimeException("브랜드 조회 중 문제가 발생했습니다.");
        }

        return contents;
    }
}
