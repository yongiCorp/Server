package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberBrandRepository extends JpaRepository<MemberBrandList,Long> {
    @Query("select mbl.brand from MemberBrandList mbl where mbl.member.id = :id ")
    List<Brand> findAllBrandByMemberId(@Param("id")Long id);

}
