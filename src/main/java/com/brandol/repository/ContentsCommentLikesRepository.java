package com.brandol.repository;

import com.brandol.domain.mapping.ContentsCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsCommentLikesRepository extends JpaRepository<ContentsCommentLikes,Long> {

    @Query("select ccl from ContentsCommentLikes  ccl where ccl.contentsComment.id = :contentsCommentId and ccl.member.id = :memberId")
    List<ContentsCommentLikes> findAllByContentsCommentIdAndMemberId(@Param("contentsCommentId")Long contentsCommentId,@Param("memberId")Long memberId);
}
