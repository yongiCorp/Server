package com.brandol.repository;

import com.brandol.domain.mapping.CommunityLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityLikesRepository extends JpaRepository<CommunityLikes,Long> {

    @Query("select cl from CommunityLikes cl where cl.community.id = :communityId and cl.member.id = :memberId")
    List<CommunityLikes> findAllByCommunityIdAndMemberId(@Param("communityId")Long communityId,@Param("memberId")Long memberId);
}
