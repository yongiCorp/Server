package com.brandol.controller;

import com.brandol.apiPayload.ApiResponse;
import com.brandol.apiPayload.code.status.SuccessStatus;
import com.brandol.converter.PointHistoryConverter;
import com.brandol.domain.PointHistory;
import com.brandol.dto.response.PointHistoryResponseDto;
import com.brandol.service.PointHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "포인트 관련 API")
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;

    @Operation(summary = "포인트 내역 조회 API",
            description = "points가 양수면 획득, 음수면 사용" +
                    "hisotory가 ITEM이면 아이템 구매, MISSION이면 포인트 미션")
    @GetMapping("/users/points-history")
    public ApiResponse<PointHistoryResponseDto.pointHistoriesDto> pointHistory(Authentication authentication){
        long memberId = Long.parseLong(authentication.getName());
        List<PointHistory> pointHistoryList = pointHistoryService.getPointHistory(memberId);
        PointHistoryResponseDto.pointHistoriesDto result = PointHistoryConverter.toPointHistories(pointHistoryList);
        return ApiResponse.onSuccess(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),result);
    }
}
