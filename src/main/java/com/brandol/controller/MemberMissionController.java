package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.config.security.PrincipalDetails;
import com.brandol.converter.MemberMissionConverter;
import com.brandol.domain.Member;
import com.brandol.domain.SurveyQuestion;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.service.MemberMissionService;
import com.brandol.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="미션 관련 API", description = "미션 조회, 미션 수락, 미션 완료 등")
public class MemberMissionController {
    private final MemberMissionService memberMissionService;
    private final SurveyService surveyService;
    @Operation(summary = "포인트 미션 목록")
    @GetMapping("/missions")
    public ApiResponse<MemberMissionResponseDto.GetMemberMissionDto> missionList(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberMissionResponseDto.GetMemberMissionDto result = memberMissionService.getMemberMission(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    @Operation(summary = "브랜드 추가 미션 도전", description = "missionSuccess가 true이면 미션 완료, false이면 미션 도전 중으로 해주시면 됩니다.")
    @PostMapping("/missions/{missionId}/add")
    public ApiResponse<MemberMissionResponseDto.MissionChallengeDto> addMissionChallenge(Authentication authentication, @PathVariable("missionId")Long missionId) {
        Long memberId = Long.parseLong(authentication.getName());
        Object details = authentication.getDetails();
        MemberMission memberMission = memberMissionService.challengeMission(memberId, missionId);
        boolean result = memberMissionService.checkBrandMission(memberId, memberMission.getMission().getBrand().getId());
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),MemberMissionConverter.toMissionChallengeDto(memberMission, result));
    }

    @Operation(summary = "포인트 미션 성공")
    @PatchMapping("/missions/{missionId}/success")
    public ApiResponse<?> missionSuccess(Authentication authentication, @PathVariable("missionId")Long missionId) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member member = principalDetails.getMember();
        memberMissionService.successMission(member.getId(), missionId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),null);
    }

    @Operation(summary = "설문지 미션 도전")
    @PostMapping("/missions/{missionId}/survey")
    public ApiResponse<MemberMissionResponseDto.SurveyMissionChallengeDto> surveyMissionChallenge(Authentication authentication, @PathVariable("missionId") Long missionId) {
        Long memberId = Long.parseLong(authentication.getName());
        memberMissionService.challengeMission(memberId, missionId);
        List<SurveyQuestion> surveyQuestions = surveyService.getSurveyQuestions(missionId);
        MemberMissionResponseDto.SurveyMissionChallengeDto surveyMissionChallengeDto = MemberMissionConverter.toSurveyMissionChallengeDto(missionId, surveyQuestions);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(),SuccessStatus._OK.getMessage(),surveyMissionChallengeDto);
    }
}
