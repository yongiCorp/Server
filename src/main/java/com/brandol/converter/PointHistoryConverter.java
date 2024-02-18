package com.brandol.converter;

import com.brandol.domain.PointHistory;
import com.brandol.dto.response.PointHistoryResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class PointHistoryConverter {
    public static PointHistoryResponseDto.pointHistoriesDto toPointHistories(List<PointHistory> pointHistoryList) {
        return PointHistoryResponseDto.pointHistoriesDto.builder()
                .result(pointHistoryList.stream()
                        .map(pointHistory -> PointHistoryResponseDto.pointHistory.builder()
                                .history(pointHistory.getHistory())
                                .points(pointHistory.getAmount())
                                .date(pointHistory.getCreatedAt().toLocalDate())
                                .build())
                        .collect(Collectors.toList())).build();
    }
}
