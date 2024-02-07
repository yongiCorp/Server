package com.brandol.dto.response;

import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.enums.MissionType;
import lombok.*;

import java.util.List;

public class MemberMissionResponseDto {

    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetMemberMissionDto {
        private List<MemberMissionPreviewDto> MemberMissionList;
        private List<MissionPreviewDto> MissionList;
    }
    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberMissionPreviewDto {
        private Long missionId;
        private String missionName;
        private int missionPoints;
        private MissionType missionType;
        private MissionStatus missionStatus;
        private String missionImage;
        private Long brandId;
    }
    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MissionPreviewDto {
        private Long missionId;
        private String missionName;
        private int missionPoints;
        private MissionType missionType;
        private String missionImage;
        private Long brandId;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MissionChallengeDto {
        private Long Id;
    }


}
