package com.brandol.service;

import com.brandol.converter.ChatConverter;
import com.brandol.domain.ChatMessages;
import com.brandol.domain.ChatRooms;
import com.brandol.dto.ChatDto;
import com.brandol.repository.ChatMessagesRepository;
import com.brandol.repository.ChatRoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatMessagesRepository chatMessagesRepository;
    private final ChatRoomsRepository chatRoomsRepository;
    private final SimpMessagingTemplate messagingTemplate;


    // 채팅 메시지 송신

    @Transactional
    public Long createChatMessage(ChatDto.ChattingDto chattingDto) {

        ChatMessages c = ChatConverter.toChatEntity(chattingDto);
        chatMessagesRepository.save(c);
        messagingTemplate.convertAndSend("/sub/chatroom/" + c.getChatRooms(), c);
        return c.getId();
    }

    // 채팅 메시지 내역 조회
    @Transactional
    public List<ChatMessages> getChatHistory(Long id) {
        System.out.println(id+"********************************************************");
        Optional<ChatRooms> a = chatRoomsRepository.findById(id);
        System.out.println(a+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        List<ChatMessages> k = chatMessagesRepository.findAllByChatRooms(a.get());
        System.out.println(k+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11111");
        return k;
    }
    /*

    // LastReadChatId 조회
    public Integer getLastReadChatId(Integer chatRoomId) {
        Integer lastReadChatId = chatRepository.findLastReadChatIdByChatRoomId(chatRoomId);
        if (lastReadChatId == null) {
            lastReadChatId = 0;
        }
        return lastReadChatId;
    }

 */
}
