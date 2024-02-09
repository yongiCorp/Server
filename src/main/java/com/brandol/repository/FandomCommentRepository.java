package com.brandol.repository;

import com.brandol.domain.mapping.FandomComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FandomCommentRepository extends JpaRepository<FandomComment,Long> {

    @Query("select fc from FandomComment fc where fc.writer.id = :memberId and fc.fandom.brand.id = :brandId")
    List<FandomComment> findFandomCommentsByMemberIdAndBrandId(@Param("memberId")Long memberId,@Param("brandId")Long brandId);

    @Query("select fc from FandomComment fc where fc.parentId = :parentId order by fc.createdAt desc ")
    List<FandomComment> findFandomCommentsByParentId(@Param("parentId")Long parentId);

    @Query("select fc from FandomComment fc where fc.fandom.id = :fandomId order by fc.createdAt asc ")
    List<FandomComment> findAllByFandomId(@Param("fandomId")Long fandomId);
}