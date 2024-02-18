package com.brandol.repository;


import com.brandol.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query(value = "SELECT * FROM items  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Items> findThreeByRandom();

    @Query(value = "SELECT * FROM items  ORDER BY RAND()",nativeQuery = true)
    List<Items> findTotalByRandom();

    @Query(value = "SELECT * FROM items i WHERE i.item_part like :itemPart ORDER BY RAND() ",nativeQuery = true)
    List<Items> finditemPartByRandom(@Param("itemPart") String itemPart);

    @Query(value = "SELECT * FROM items i  ORDER BY CASE WHEN i.items_id = :itemId THEN 0 ELSE 1 END, RAND()",nativeQuery = true)
    List<Items> finditemByIdandRandom(@Param("itemId") Long itemId);

    List<Items> findByNameContaining(String searchContents);


}
