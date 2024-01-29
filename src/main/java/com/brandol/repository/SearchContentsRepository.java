package com.brandol.repository;


import com.brandol.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface SearchContentsRepository extends JpaRepository<Contents, Long> {


    //@Query("SELECT Count(*) FROM ContentsLikes l WHERE l.contents= :contents_id ")
    @Query(value = "SELECT count(cl) FROM ContentsLikes cl WHERE cl.contents.id = :contentsId")
    Long countLikesByContents_id(Long contentsId);

    @Query(value = "SELECT Count(cc) FROM ContentsComment cc WHERE cc.contents.id= :contentsId")
    Long countCommentsByContents_id(Long contentsId);
}


