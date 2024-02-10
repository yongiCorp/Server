package com.brandol.repository;

import com.brandol.domain.mapping.CommunityCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityCommentsLikesRepository extends JpaRepository<CommunityCommentLikes,Long> {

    @Query("select ccl from CommunityCommentLikes ccl where ccl.communityComment.id = :communityCommentId and ccl.member.id = :memberId")
    List<CommunityCommentLikes> findAllByCommunityCommentIdAndMemberId(@Param("communityCommentId")Long communityCommentId, @Param("memberId")Long memberId);
}
