package com.brandol.converter;

import com.brandol.domain.ChatMessages;
import com.brandol.dto.response.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatConverter {

    public static ChatResponseDto.ChatMessagesDto toChatMessagesDto(List<ChatResponseDto.ChatSingleMessageDto> dtoList, Long lastIndex){
        return ChatResponseDto.ChatMessagesDto.builder()
                .messageDtoList(dtoList)
                .lastIndex(lastIndex)
                .build();
    }

    public static ChatResponseDto.ChatSingleMessageDto toChatSingleMessageDto(ChatMessages chatMessages){
        return ChatResponseDto.ChatSingleMessageDto.builder()
                .memberId(chatMessages.getSender().getId())
                .memberName(chatMessages.getSender().getNickname())
                .memberAvatar(chatMessages.getSender().getAvatar())
                .content(chatMessages.getMessage())
                .time(chatMessages.getCreatedAt())
                .build();
    }
}
