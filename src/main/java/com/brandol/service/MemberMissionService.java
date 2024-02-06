package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.MemberMissionConverter;
import com.brandol.domain.Member;
import com.brandol.domain.Mission;
import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.repository.MemberMissionRepository;
import com.brandol.repository.MemberRepository;
import com.brandol.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionService {
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    public MemberMissionResponseDto.GetMemberMissionDto getMemberMission(Long memberId) {
        List<MemberMission> memberMissionList = memberMissionRepository.findByMemberIdWithMission(memberId);
        List<Mission> missionList = missionRepository.findMissionNotBelongingToMember(memberId);
        return MemberMissionConverter.to(memberMissionList, missionList);
    }
    @Transactional
    public MemberMissionResponseDto.MissionChallengeDto challengeMission(Long memberId, Long missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MISSION));
        Member member = memberRepository.findOneById(memberId);
        MemberMission memberMission = MemberMission.builder().member(member).mission(mission).missionStatus(MissionStatus.CHALLENGING).build();
        memberMissionRepository.save(memberMission);
        return MemberMissionResponseDto.MissionChallengeDto.builder().Id(memberMission.getId()).build();
    }
}
