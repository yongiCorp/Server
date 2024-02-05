package com.brandol.repository;

import com.brandol.domain.mapping.ContentsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsCommentRepository extends JpaRepository<ContentsComment,Long> {
    @Query("select cc from ContentsComment cc where cc.writer.id = :memberId")
    List<ContentsComment> findContentsCommentByMemberId(@Param("memberId")Long memberId);
}