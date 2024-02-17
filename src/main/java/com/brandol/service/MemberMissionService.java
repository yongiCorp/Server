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
    private final BrandRepository brandRepository;
    public MemberMissionResponseDto.GetMemberMissionDto getMemberMission(Long memberId) {
        List<MemberMission> memberMissionList = memberMissionRepository.findByMemberId(memberId);
        List<Mission> missionList = missionRepository.findMissionNotBelongingToMember(memberId);
        return MemberMissionConverter.to(memberMissionList, missionList);
    }
    @Transactional
    public boolean checkBrandMission(Long memberId, Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND));
        //미션 존재 여부 확인
        Mission mission = missionRepository.findByBrandAndMissionType(brand, MissionType.ADD).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MISSION));
        List<MemberBrandList> memberBrandLists = memberBrandRepository.findOneByMemberIdAndBrandId(memberId, brandId);
        if(memberBrandLists.isEmpty()) return false;
        Optional<MemberMission> memberMission = memberMissionRepository.findByMemberIdAndMissionId(memberId, mission.getId());
        memberMission.ifPresent(value -> value.changeStatus(MissionStatus.COMPLETED));
        return memberMission.isPresent();
    }


    @Transactional
    public MemberMission challengeMission(Long memberId, Long missionId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        Mission mission = missionRepository.findById(missionId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MISSION));
        MemberMission memberMission = MemberMission.builder().member(member).mission(mission).missionStatus(MissionStatus.CHALLENGING).build();
        memberMissionRepository.save(memberMission);
        return memberMission;
    }

    @Transactional
    public MemberMission successMission(Long memberId, Long missionId) {
        MemberMission memberMission = memberMissionRepository.findAllByMemberIdAndMissionId(memberId, missionId);
        if(memberMission == null) throw new ErrorHandler(ErrorStatus._NOT_CHALLENGING_MISSION);
        if(memberMission.getMissionStatus() == MissionStatus.CHALLENGING) throw new ErrorHandler(ErrorStatus._NOT_COMPLETED_MISSION);
        else {
            memberMission.changeStatus(MissionStatus.COMPLETED);
        }
        memberMission.getMember().updatePoint(memberMission.getMission().getPoints());
        return memberMission;
    }

    @Transactional
    public boolean checkCommunityMission(Long memberId, Long brandId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_BRAND));
        Mission mission = missionRepository.findByBrandAndMissionType(brand, MissionType.COMMUNITY).orElseThrow(() -> new ErrorHandler(ErrorStatus._NOT_EXIST_MISSION));
        Optional<Community> community = communityRepository.findByMemberAndBrand(member, brand);
        if(community.isEmpty()) return false;
        Optional<MemberMission> memberMission = memberMissionRepository.findByMemberIdAndMissionId(memberId, mission.getId());
        memberMission.ifPresent(value -> value.changeStatus(MissionStatus.COMPLETED));
        return true;
    }

}
