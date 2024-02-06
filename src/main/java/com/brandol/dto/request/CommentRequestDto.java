package com.brandol.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

public class CommentRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class addComment {
        @Schema(description = "댓글 내용")
        @NotNull
        private String content;
    }
}
