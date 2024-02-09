package com.brandol.repository;

import com.brandol.domain.mapping.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment,Long>{
    @Query("select cc from CommunityComment cc where cc.writer.id = :memberId and cc.community.brand.id = :brandId")
    List<CommunityComment> findCommunityCommentByMemberIdAndBrandId(@Param("memberId") Long memberId,@Param("brandId")Long brandId);

    @Query("select cc from CommunityComment cc where  cc.parentId = :parentId order by cc.createdAt desc")
    List<CommunityComment> findCommunityCommentsByParentId(@Param("parentId")Long parentId);

    @Query("select cc from CommunityComment cc where  cc.community.id = :communityId order by  cc.createdAt asc")
    List<CommunityComment> findAllByCommunityId(@Param("communityId")Long CommunityId);

}
