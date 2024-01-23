package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.dto.subDto.BrandList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberBrandRepository extends JpaRepository<MemberBrandList,Long> {
    @Query("select mbl.brand from MemberBrandList mbl where mbl.member.id = :id ")
    List<Brand> findAllBrandByMemberId(@Param("id")Long id);

    @Query("select mbl from MemberBrandList mbl where mbl.member.id = :id")
    List<MemberBrandList> findAllByMemberId(@Param("id")Long id);

    @Query("select mbl from MemberBrandList mbl where mbl.brand.id = :id order by mbl.sequence")
    List<MemberBrandList> getBrandJoinedFanCount(@Param("id")Long id, Pageable pageable);

}
