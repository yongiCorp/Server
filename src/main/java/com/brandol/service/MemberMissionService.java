package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.MemberMissionConverter;
import com.brandol.domain.Member;
import com.brandol.domain.Mission;
import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.enums.MissionType;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.repository.MemberBrandRepository;
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
    private final MemberBrandRepository memberBrandRepository;
    private final MemberRepository memberRepository;
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
        //멤버 브랜드 리스트에 브랜드가 존재하는지 확인하지 않아서 오류 발생 <- 이거 해야됨
        //위 코드는 브랜드Id에 해당하는 미션 존애 유무 확인
        //memberId와 미션id로 사용자가 미션을 도전중이지 확인 -> 도전 중이면 true 반환 -> 틀렸음
        //
    }


    @Transactional
    public MemberMission challengeMission(Long memberId, Long missionId) {
        Member member = memberRepository.findOneById(memberId);
        Mission mission = missionRepository.findById(missionId).orElseThrow(()-> new ErrorHandler(ErrorStatus._NOT_EXIST_MISSION));
        MemberMission memberMission = MemberMission.builder().member(member).mission(mission).missionStatus(MissionStatus.CHALLENGING).build();
        memberMissionRepository.save(memberMission);
        return memberMission;
    }
}
