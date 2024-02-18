package com.brandol.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ChatMessageRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class sendMessage{

        @Schema(description = "채팅 내용")
        @NotNull
        private String content;
    }
}
