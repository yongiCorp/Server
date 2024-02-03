package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    @Query(value = "select b from Brand b where b.id = :id ")
    Brand findOneById(@Param("id") Long id);


    @Query(value = "select * from Brand b order by created_at DESC LIMIT :cnt ",nativeQuery = true)
    List<Brand> findRecentBrands(@Param("cnt") int cnt);

    @Query(value = "select b from Brand b WHERE b.name = :name")
    Brand findOneByName(@Param("name") String name);


    @Query(value ="select * from Brand b where b.name not like :name order by created_at DESC LIMIT :cnt",nativeQuery = true)
    List<Brand> findRecentBrandsExceptForOne(@Param("name")String name,@Param("cnt")int cnt);

    @Query(value = "SELECT * FROM Brand  ORDER BY RAND() limit 3",nativeQuery = true)
    List<Brand> findThreeByRandom();

    @Query(value = "SELECT * FROM Brand  ORDER BY RAND() ",nativeQuery = true)
    List<Brand> findAllByRandom();
    boolean existsById(Long id);
}
