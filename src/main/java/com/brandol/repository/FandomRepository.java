package com.brandol.repository;

import com.brandol.domain.Fandom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FandomRepository extends JpaRepository<Fandom,Long> {

    @Query("select f from Fandom f where f.brand.id= :brandId and f.fandomType = com.brandol.domain.enums.FandomType.CULTURE order by f.createdAt desc ")
    List<Fandom> getSomeRecentFandomCultures(@Param("brandId")Long brandId, Pageable pageable);

    @Query("select f from Fandom f where f.brand.id= :brandId and f.fandomType = com.brandol.domain.enums.FandomType.ANNOUNCEMENT order by f.createdAt desc ")
    List<Fandom> getSomeRecentFandomNotices(@Param("brandId")Long brandId, Pageable pageable);

    @Query("select f from  Fandom f where f.brand.id = :brandId and f.fandomType = com.brandol.domain.enums.FandomType.CULTURE")
    List<Fandom> getFandomCulturesByBrandId(@Param("brandId")Long brandId,Pageable pageable);
}
