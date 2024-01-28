package com.brandol.repository;

import com.brandol.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchBrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "SELECT * FROM Brand  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Brand> findThreeByRandom();
}
