package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    @Query(value = "select b from Brand b where b.id = :id ")
    Brand findOneById(@Param("id") Long id);


    @Query(value = "select * from brand b order by created_at DESC LIMIT :cnt ",nativeQuery = true)
    List<Brand> findRecentBrands(@Param("cnt") int cnt);

    @Query(value = "select b from Brand b WHERE b.name = :name")
    Brand findOneByName(@Param("name") String name);


    @Query(value ="select * from brand b where b.name not like :name and b.name not like '일반' order by created_at DESC LIMIT :cnt",nativeQuery = true)
    List<Brand> findRecentBrandsExceptForOne(@Param("name")String name,@Param("cnt")int cnt);

    @Query(value = "SELECT * FROM brand WHERE brand.brand_id not like 3 ORDER BY RAND() ",nativeQuery = true)
    List<Brand> findByRandom(); //0216 수정(특정브랜드제외)


    List<Brand> findByNameContaining(String searchKeyword);

    boolean existsById(Long id);
}
