package com.brandol.repository;

import com.brandol.domain.mapping.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityImageRepository extends JpaRepository<CommunityImage,Long> {

    @Query("select ci from CommunityImage ci where ci.community.id = :communityId")
    List<CommunityImage> findAllByCommunityId(@Param("communityId")Long communityId);

    List<CommunityImage> findByCommunityIdIn(List<Long> communityIds);

}
