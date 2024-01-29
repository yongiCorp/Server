package com.brandol.repository;

import com.brandol.domain.ContentsImage;
import com.brandol.domain.FandomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentImageRepository extends JpaRepository<ContentsImage,Long> {

    @Query("select ci from ContentsImage ci where ci.contents.id = :contentsId order by ci.createdAt asc ")
    List<ContentsImage> findContentsImages(@Param("contentsId")Long contentsId);
}
