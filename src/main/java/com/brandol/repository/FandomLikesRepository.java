package com.brandol.repository;

import com.brandol.domain.mapping.FandomLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FandomLikesRepository extends JpaRepository<FandomLikes,Long> {

    @Query("select fl from FandomLikes fl where fl.fandom.id = :fandomId and fl.member.id = :memberId")
    List<FandomLikes> findAllByFandomIdAndMemberId(@Param("fandomId")Long fandomId,@Param("memberId")Long memberId);
}
