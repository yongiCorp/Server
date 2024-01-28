package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchContentsRepository extends JpaRepository<Contents, Long> {

    /*
    @Query("SELECT c, COUNT(cl), COUNT(cc) FROM Contents c " +
            "LEFT JOIN ContentsLikes cl " +
            "LEFT JOIN ContentsComment cc " +
            "WHERE c.id = :contentsId " +
            "GROUP BY c")
    List<Object[]> findContentsDetails(@Param("contentsId") Long Id);

     */

    //@Query("SELECT Count(*) FROM ContentsLikes l WHERE l.contents= :contents_id ")
    @Query(value = "SELECT count(cl) FROM ContentsLikes cl WHERE cl.contents.id = :contentsId")
    Long countBycontents_id(@Param("contentsId") Long contentsId);

    @Query("SELECT Count(c.id) FROM ContentsComment c WHERE c.contents= :contentId")
    Integer countByContentsComment(@Param("contentId") Long contentId);
}


