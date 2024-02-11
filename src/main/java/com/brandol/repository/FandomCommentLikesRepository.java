package com.brandol.repository;

import com.brandol.domain.mapping.FandomCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FandomCommentLikesRepository extends JpaRepository<FandomCommentLikes,Long> {

    @Query("select fcl from FandomCommentLikes fcl where fcl.fandomComment.id = :fandomCommentId and fcl.member.id = :memberId")
    List<FandomCommentLikes> findAllByFandomCommentIdAndMemberId(@Param("fandomCommentId")Long fandomCommentId,@Param("memberId")Long memberId);
}
