package com.brandol.repository;

import com.brandol.domain.Brand;
import com.brandol.domain.Mission;
import com.brandol.domain.enums.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("select m from Mission m where m.id not in (select mm.mission.id from MemberMission mm where mm.member.id = :memberId)")
    List<Mission> findMissionNotBelongingToMember(@Param("memberId") Long memberId);

    Optional<Mission> findById(Long missionId);

    Optional<Mission> findByBrandAndMissionType(Brand brand, MissionType missionType);

    List<Mission> findAllByBrand(Brand brand);

}
