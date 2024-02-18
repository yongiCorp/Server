package com.brandol.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ChatResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatMessagesDto{
        private List<ChatSingleMessageDto> messageDtoList;
        private Long lastIndex;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatSingleMessageDto{
        private Long memberId;
        private String memberName;
        private String memberAvatar;
        private String content;
        private LocalDateTime time;
    }
}
