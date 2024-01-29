package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import com.brandol.domain.Fandom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents,Long> {
    @Query(value = "select * from contents c order by created_at desc LIMIT :cnt ",nativeQuery = true)
    List<Contents> findRecentBrands(@Param("cnt") int cnt);

    @Query("select c from Contents c where c.brand.id= :brandId and c.contentsType = com.brandol.domain.enums.ContentsType.CARDNEWS order by c.createdAt desc ")
    List<Contents> getSomeRecentCardNews(@Param("brandId")Long brandId, Pageable pageable);

    @Query("select c from Contents c where c.brand.id= :brandId and c.contentsType = com.brandol.domain.enums.ContentsType.EVENTS order by c.createdAt desc ")
    List<Contents> getSomeRecentEvents(@Param("brandId")Long brandId, Pageable pageable);

    @Query("select c from Contents c where c.brand.id= :brandId and c.contentsType = com.brandol.domain.enums.ContentsType.VIDEOS order by c.createdAt desc ")
    List<Contents> getSomeRecentVideos(@Param("brandId")Long brandId, Pageable pageable);
}
