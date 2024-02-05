package com.brandol.repository;

import com.brandol.domain.mapping.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment,Long>{
    @Query("select cc from CommunityComment cc where cc.writer.id = :memberId")
    List<CommunityComment> findCommunityCommentByMemberId(@Param("memberId") Long memberId);

}
