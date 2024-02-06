package com.brandol.service;

import com.brandol.converter.MemberMissionConverter;
import com.brandol.domain.Mission;
import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.enums.MissionType;
import com.brandol.domain.mapping.MemberBrandList;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.repository.MemberBrandRepository;
import com.brandol.repository.MemberMissionRepository;
import com.brandol.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionService {
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final MemberBrandRepository memberBrandRepository;
    public MemberMissionResponseDto.GetMemberMissionDto getMemberMission(Long memberId) {
        List<MemberMission> memberMissionList = memberMissionRepository.findByMemberId(memberId);
        List<Mission> missionList = missionRepository.findMissionNotBelongingToMember(memberId);
        return MemberMissionConverter.to(memberMissionList, missionList);
    }
    @Transactional
    public void checkAddMission(Long memberId) {
        List<MemberMission> missionList = memberMissionRepository.findAddMissionByMemberId(memberId, MissionType.ADD);
        missionList.forEach(memberMission -> {
            Long brandId = memberMission.getMission().getBrand().getId();
            List<MemberBrandList> oneByMemberIdAndBrandId = memberBrandRepository.findOneByMemberIdAndBrandId(memberId, brandId);
            if(!oneByMemberIdAndBrandId.isEmpty()) {
                memberMission.changeStatus(MissionStatus.COMPLETED);
            }
        });
    }

}
