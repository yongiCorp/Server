package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.converter.MemberMissionConverter;
import com.brandol.domain.mapping.MemberMission;
import com.brandol.dto.response.MemberMissionResponseDto;
import com.brandol.service.MemberMissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="미션 관련 API", description = "미션 조회, 미션 수락, 미션 완료 등")
public class MemberMissionController {
    private final MemberMissionService memberMissionService;
    @Operation(summary = "포인트 미션 목록")
    @GetMapping("/users/missions")
    public ApiResponse<MemberMissionResponseDto.GetMemberMissionDto> missionList(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberMissionResponseDto.GetMemberMissionDto result = memberMissionService.getMemberMission(memberId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    @Operation(summary = "포인트 미션 도전")
    @PostMapping("/users/missions/{missionId}")
    public ApiResponse<MemberMissionResponseDto.MissionChallengeDto> missionChallenge(Authentication authentication, @PathVariable("missionId")Long missionId) {
        Long memberId = Long.parseLong(authentication.getName());
        //MemberMissionResponseDto.MissionChallengeDto missionChallengeDto = memberMissionService.challengeMission(memberId, missionId);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),null);//missionChallengeDto);
    }
}
