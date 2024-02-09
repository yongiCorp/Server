package com.brandol.converter;

import com.brandol.domain.ChatRooms;
import com.brandol.domain.mapping.MyItem;
import com.brandol.dto.ChatDto;
import com.brandol.dto.response.MyItemResponseDto;

public class ChatConverter {

    public static ChatDto.ChatroomDto toChatDTO(ChatRooms c) {

        return ChatDto.ChatroomDto.builder()
                .crewId(c.getUserB().getId())
                .userId(c.getUserA().getId())
                .build();
    }
}
