package com.brandol.repository;

import com.brandol.domain.FandomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FandomImageRepository extends JpaRepository<FandomImage,Long> {

    @Query("select fi from FandomImage fi where fi.fandom.id = :fandomId order by fi.createdAt asc ")
    List<FandomImage> findFandomImages(@Param("fandomId")Long fandomId);
}
