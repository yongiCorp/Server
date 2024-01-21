package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class BrandRepository {

    private final EntityManager em;

    public Long save(Brand brand){
        em.persist(brand);
        return brand.getId();
    }

    @Transactional(readOnly = true)
    public Brand findByOneId(Long id){ //Brand들 중 id를 기준으로 검색하는 함수
        List<Brand> brands = em.createQuery("select b from Brand b where b.id =: id", Brand.class)
                .setParameter("id",id).getResultList();
        int len = brands.size();

        if( len == 0|| len >1){
            throw new RuntimeException("브랜드 조회 중 문제가 발생했습니다.");
        }
        return brands.get(0);
    }


    @Transactional(readOnly = true) //읽기 성능 향상 목적
    public List<Brand> findRecentBrands(int cnt){ //최신 등록 Brand들 중 cnt개의 brand List를 리턴하는 함수

        List<Brand> brands = em.createQuery("select b from Brand b order by b.createdAt DESC ",Brand.class)
                .setMaxResults(cnt).getResultList();

        int len = brands.size();
        if(len == 0){
            throw new RuntimeException("브랜드 조회 중 문제가 발생했습니다.");
        }

        return brands;
    }


    @Transactional(readOnly = true) //읽기 성능 향상 목적
    public List<Brand> findRecentBrandsExceptForBrandol(int cnt){ //최신 등록 Brand들 중 'brandol' 브랜드를 제외하고 cnt개의 brand List를 리턴하는 함수

        List<Brand> brands = em.createQuery("select b from Brand b  where b.name not like '%brandol%' order by b.createdAt DESC ",Brand.class)

                .setMaxResults(cnt).getResultList();

        int len = brands.size();
        if(len == 0){
            throw new RuntimeException("브랜드 조회 중 문제가 발생했습니다.");
        }

        return brands;
    }

    @Transactional(readOnly = true) //읽기 성능 향상 목적
    public Brand findBrandByName(String name){ //Brand들 중 이름을 기준으로 검색하는 함수

        List<Brand> brands = em.createQuery("select b from Brand b where b.name =: name",Brand.class)
                .setParameter("name",name).getResultList();

        int len = brands.size();
        if(len == 0 || len >1){
            throw new RuntimeException("해당 브랜드 조회 중 문제가 발생했습니다.");
        }


        return brands.get(0);
    }

    @Transactional(readOnly = true)
    public Boolean isExistBrand(Long id){
        List<Brand> brands = em.createQuery("select b from Brand b where b.id =: id", Brand.class)
                .setParameter("id",id).getResultList();
        int len = brands.size();

        if(len >= 1 ){
            return true;
        }
        return false;
    }


}
