package com.brandol.dto;

import lombok.*;

public class ChatDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChatroomDto{
        private String content;
        private Integer chatRoomId;
        private Long crewId;
        private Long userId;
    }
}
