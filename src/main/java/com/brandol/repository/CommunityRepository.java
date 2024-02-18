package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community,Long> {

    @Query("select c from Community c where c.brand.id = :brandId and c.communityType = com.brandol.domain.enums.CommunityType.ALL order by c.createdAt desc ")
    List<Community> findRecentFreeBoard(@Param("brandId") Long brandId,Pageable pageable );

    @Query("select c from Community c where c.brand.id = :brandId and c.communityType = com.brandol.domain.enums.CommunityType.FEEDBACK order by c.createdAt desc ")
    List<Community> findRecentFeedBackBoard(@Param("brandId") Long brandId,Pageable pageable);

    Page<Community> findByMember(Member member, PageRequest pageRequest);

    Optional<Community> findByMemberAndBrand(Member member, Brand brand);
}
