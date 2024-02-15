package com.brandol.dto.response;

import com.brandol.domain.SurveyExample;
import com.brandol.domain.enums.MissionStatus;
import com.brandol.domain.enums.MissionType;
import com.brandol.domain.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Optional;

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
        private boolean missionSuccess;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SurveyMissionPreviewDto {
        private Long surveyQuestionId;
        private String surveyQuestion;
        private QuestionType surveyQuestionType;
        private List<SurveyExamplePreviewDto> surveyExamples;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SurveyMissionChallengeDto {
        private Long missionId;
        private List<SurveyMissionPreviewDto> survey;
    }
    @Builder
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SurveyExamplePreviewDto {
        private Long surveyExampleId;
        private String surveyExample;
    }

}
