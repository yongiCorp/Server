package com.brandol.repository;


import com.brandol.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchAvatarstoreRepository extends JpaRepository<Items, Long> {
    @Query(value = "SELECT * FROM Items  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Items> findThreeByRandom();
}
