package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.MemberMissionConverter;
import com.brandol.domain.Brand;
import com.brandol.domain.Member;
import com.brandol.domain.Mission;
import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.enums.MissionType;
import com.brandol.domain.mapping.Community;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.request.MemberMissionRequestDto;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.repository.*;
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
    private final MemberBrandRepository memberBrandRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;
    public MemberMissionResponseDto.GetMemberMissionDto getMemberMission(Long memberId) {
        List<MemberMission> memberMissionList = memberMissionRepository.findByMemberId(memberId);
        List<Mission> missionList = missionRepository.findMissionNotBelongingToMember(memberId);
        return MemberMissionConverter.to(memberMissionList, missionList);
    }
    @Transactional
    public boolean checkBrandMission(Long memberId, Long brandId) {
        List<MemberBrandList> oneByMemberIdAndBrandId = memberBrandRepository.findOneByMemberIdAndBrandId(memberId, brandId);
        if(oneByMemberIdAndBrandId.isEmpty()) return false;
        return missionRepository.findByBrand_Id(brandId)
                .map(mission -> memberMissionRepository.findByMemberIdAndMissionId(memberId, mission.getId())
                        .map(memberMission -> {
                            memberMission.changeStatus(MissionStatus.COMPLETED);
                            return true;
                        }).orElse(false))
                .orElse(false);
    }


    @Transactional
    public MemberMission challengeMission(Long memberId, Long missionId) {
        Member member = memberRepository.findOneById(memberId);
        Mission mission = missionRepository.findById(missionId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MISSION));
        MemberMission memberMission = MemberMission.builder().member(member).mission(mission).missionStatus(MissionStatus.CHALLENGING).build();
        memberMissionRepository.save(memberMission);
        return memberMission;
    }

    @Transactional
    public MemberMission successMission(Long memberId, Long missionId) {
        MemberMission memberMission = memberMissionRepository.findAllByMemberIdAndMissionId(memberId, missionId);
        if(memberMission == null) throw new ErrorHandler(ErrorStatus._NOT_CHALLENGING_MISSION);
        if(memberMission.getMissionStatus() == MissionStatus.ENDED) throw new ErrorHandler(ErrorStatus._ALREADY_COMPLETED_MISSION);
        memberMission.changeStatus(MissionStatus.ENDED);
        memberMission.getMember().updatePoint(memberMission.getMission().getPoints());
        return memberMission;
    }

    @Transactional
    public boolean checkCommunityMission(MemberMission memberMission){
        Brand brand = memberMission.getMission().getBrand();
        Member member = memberMission.getMember();
        Optional<Community> community = communityRepository.findByMemberAndBrand(member, brand);
        if(community.isPresent()) {
            memberMission.changeStatus(MissionStatus.COMPLETED);
            return true;
        }
        return false;
    }

}
