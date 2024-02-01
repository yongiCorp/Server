package com.brandol.repository;


import com.brandol.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents,Long> {
    @Query(value = "select * from contents c order by created_at desc LIMIT :cnt ",nativeQuery = true)
    List<Contents> findRecentBrands(@Param("cnt") int cnt);

    @Query(value = "SELECT * FROM Contents  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Contents> findThreeByRandom();
}
