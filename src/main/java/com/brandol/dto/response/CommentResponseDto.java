package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentPackageDto{

        public CommentDto parentDto;
        public List<CommentDto> childDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentDto{

        public Long commentId;
        public Long parentId;
        public Long depth;
        public Long writerId;
        public String writerName;
        public String writerProfile;
        public String content;
        private int likeCount;
        public LocalDateTime writtenDate;
    }
}
