package com.brandol.repository;

import com.brandol.domain.mapping.ContentsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsCommentRepository extends JpaRepository<ContentsComment,Long> {
    @Query("select cc from ContentsComment cc where cc.writer.id = :memberId and cc.contents.brand.id = :brandId")
    List<ContentsComment> findContentsCommentByMemberIdAndBrandId(@Param("memberId")Long memberId,@Param("brandId")Long brandId);

    @Query("select cc from ContentsComment cc where cc.parentId = :parentId order by cc.createdAt")
    List<ContentsComment> findContentsCommentsByParentId(@Param("parentId")Long parentId);

    @Query("select cc from ContentsComment cc where cc.contents.id = :contentsId order by cc.createdAt asc ")
    List<ContentsComment> findAllByContentsId(@Param("contentsId")Long contentsId);
}