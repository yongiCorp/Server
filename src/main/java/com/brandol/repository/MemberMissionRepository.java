package com.brandol.repository;

import com.brandol.domain.mapping.MemberMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    @Query("select m from MemberMission m join fetch m.mission where m.member.id = :memberId")
    List<MemberMission> findByMemberIdWithMission(@Param("memberId") Long memberId);
}
