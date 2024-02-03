package com.brandol.repository;

import com.brandol.domain.Contents;
import com.brandol.domain.ContentsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentImageRepository extends JpaRepository<Contents,Long> {

    @Query(value = "select ci from ContentsImage ci where ci.contents.id = :contentsId")
    List<ContentsImage> findAllByContentsId(@Param("contentsId")Long contentsId);
}