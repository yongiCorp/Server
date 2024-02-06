package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ContentsResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ContentsDto{
        private Long writerId;
        private String writerName;
        private String writerProfile;
        private Long contentsId;
        private String title;
        private String content;
        private List<String> images;
        private String file;
        private int likeCount;
        private int commentCount;
        private LocalDateTime writtenDate;
    }
}
