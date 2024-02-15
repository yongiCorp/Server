package com.brandol.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ChatDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChattingDto{



        private String content;
        private Long chatRoomId;
        //private String senderName;
        private Long senderId;
        private Long sendedId;
        private String sendTime;


    }
}
