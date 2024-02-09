package com.brandol.repository;

import com.brandol.domain.Member;
import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.enums.MissionType;
import com.brandol.domain.mapping.MemberMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    @Query("select m from MemberMission m join fetch m.mission where m.member.id = :memberId")
    List<MemberMission> findByMemberId(@Param("memberId") Long memberId);

    @Query("select m from MemberMission m join fetch m.mission where m.member.id = :memberId and m.mission.missionType = :type")
    List<MemberMission> findAddMissionByMemberId(@Param("memberId") Long memberId, @Param("type") MissionType type);

    Optional<MemberMission> findByMemberIdAndMissionId(Long memberId, Long missionId);
    @Query("SELECT mm FROM MemberMission mm JOIN FETCH mm.member m JOIN FETCH mm.mission ms WHERE m.id = :memberId AND ms.id = :missionId")
    MemberMission findAllByMemberIdAndMissionId(@Param("memberId") Long memberId, @Param("missionId") Long missionId);

}
