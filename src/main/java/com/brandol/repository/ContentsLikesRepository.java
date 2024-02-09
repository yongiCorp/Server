package com.brandol.repository;

import com.brandol.domain.mapping.ContentsLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsLikesRepository extends JpaRepository<ContentsLikes,Long> {

    @Query("select cl from ContentsLikes cl where  cl.contents.id = :contentsId and cl.member.id = :memberId")
    List<ContentsLikes>findAllByContentsIdAndMemberId(@Param("contentsId")Long contentsId,@Param("memberId")Long memberId);
}
