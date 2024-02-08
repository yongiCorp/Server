package com.brandol.converter;

import com.brandol.domain.Mission;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.dto.response.MemberResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class MemberMissionConverter {
    public static MemberMissionResponseDto.GetMemberMissionDto to(List<MemberMission> memberMissions, List<Mission> missions) {
        return MemberMissionResponseDto.GetMemberMissionDto.builder()
                .MemberMissionList(toMemberMissionDto(memberMissions))
                .MissionList(toMissionDto(missions))
                .build();
    }
    public static List<MemberMissionResponseDto.MemberMissionPreviewDto> toMemberMissionDto(List<MemberMission> memberMissions) {
        return memberMissions.stream()
                .map(memberMission -> MemberMissionResponseDto.MemberMissionPreviewDto.builder()
                        .missionId(memberMission.getMission().getId())
                        .brandId(memberMission.getMission().getBrand().getId())
                        .missionName(memberMission.getMission().getName())
                        .missionPoints(memberMission.getMission().getPoints())
                        .missionType(memberMission.getMission().getMissionType())
                        .missionStatus(memberMission.getMissionStatus())
                        .missionImage(memberMission.getMission().getImage())
                        .build())
                .collect(Collectors.toList());
    }
    public static List<MemberMissionResponseDto.MissionPreviewDto> toMissionDto(List<Mission> missions) {
        return missions.stream()
                .map(mission -> MemberMissionResponseDto.MissionPreviewDto.builder()
                        .missionId(mission.getId())
                        .brandId(mission.getBrand().getId())
                        .missionName(mission.getName())
                        .missionPoints(mission.getPoints())
                        .missionType(mission.getMissionType())
                        .missionImage(mission.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    public static MemberMissionResponseDto.MissionChallengeDto toMissionChallengeDto(MemberMission memberMission, Boolean result) {
        return MemberMissionResponseDto.MissionChallengeDto.builder()
                .Id(memberMission.getId())
                .missionSuccess(result)
                .build();
    }
}
