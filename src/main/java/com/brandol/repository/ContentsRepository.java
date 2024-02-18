package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents,Long> {
    @Query(value = "select * from contents c order by created_at desc LIMIT :cnt ",nativeQuery = true)
    List<Contents> findRecentBrands(@Param("cnt") int cnt);

    @Query(value = "select c from Contents c where c.contentsType =com.brandol.domain.enums.ContentsType.EVENTS order by c.createdAt desc")
    List<Contents>findRecentBrandEventsForSubBanner(Pageable pageable);

    @Query(value = "select  c from  Contents c where c.brand.id= :brandId and c.contentsType =com.brandol.domain.enums.ContentsType.EVENTS order by c.createdAt desc ")
    List<Contents>findRecentEvents(@Param("brandId")Long brandId, Pageable pageable);

    @Query(value = "select  c from  Contents c where c.brand.id= :brandId and c.contentsType =com.brandol.domain.enums.ContentsType.CARDNEWS order by c.createdAt desc ")
    List<Contents>findRecentCardNews(@Param("brandId")Long brandId,Pageable pageable);

    @Query(value = "select  c from  Contents c where c.brand.id= :brandId and c.contentsType =com.brandol.domain.enums.ContentsType.VIDEOS order by c.createdAt desc ")
    List<Contents>findRecentVideos(@Param("brandId")Long brandId,Pageable pageable);

    
    @Query(value = "SELECT * FROM contents  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Contents> findThreeByRandom();
    @Query(value = "SELECT * FROM contents  ORDER BY RAND()",nativeQuery = true)
    List<Contents> findAllByRandom();

    List<Contents> findByTitleContaining(String searchContents);
}
