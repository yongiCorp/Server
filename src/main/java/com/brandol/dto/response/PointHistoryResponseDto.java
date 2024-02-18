package com.brandol.dto.response;

import com.brandol.domain.enums.HistoryType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class PointHistoryResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class pointHistoriesDto {
        List<pointHistory> result;
    }
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class pointHistory{
        HistoryType history;
        int points;
        LocalDate date;
    }
}
