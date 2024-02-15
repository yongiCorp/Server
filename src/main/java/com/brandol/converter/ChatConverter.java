package com.brandol.converter;

import com.brandol.domain.ChatMessages;
import com.brandol.domain.ChatRooms;
import com.brandol.dto.ChatDto;
import com.brandol.repository.ChatRoomsRepository;

import javax.persistence.EntityNotFoundException;

public class ChatConverter {

    static ChatRoomsRepository chatRoomsRepository;

    public static ChatDto.ChattingDto toChattingDTO(ChatMessages c) {

        return ChatDto.ChattingDto.builder()
                .senderId(c.getSender())
                .sendedId(c.getSended_id())
                .sendTime(c.getCreatedAt().toString())
                .content(c.getMessage())
                .chatRoomId(c.getChatRooms().getId())
                .build();
    }
//매핑엔ㅌ티필요
    public static ChatMessages toChatEntity(ChatDto.ChattingDto ch){

        ChatRooms chatRoom = chatRoomsRepository.findById(ch.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("ChatRoom not found with id: " + ch.getChatRoomId()));

        return ChatMessages.builder()
                .sender(ch.getSenderId())
                .sended_id(ch.getSendedId())
                .sendTime(ch.getSendTime())//
                .message(ch.getContent())
                .chatRooms(chatRoom)
                .build();
    }
}
