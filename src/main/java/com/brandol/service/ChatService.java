package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.domain.ChatMessages;
import com.brandol.domain.ChatRooms;
import com.brandol.domain.Member;
import com.brandol.dto.request.ChatMessageRequestDto;
import com.brandol.repository.ChatMessageRepository;
import com.brandol.repository.ChatRoomsRepository;
import com.brandol.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final MemberRepository memberRepository;
    private final ChatRoomsRepository chatRoomsRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public Long makeChatRoom(Long firstMemberId,Long secondMemberId){

        Member memberA = memberRepository.findById(firstMemberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        Member memberB = memberRepository.findById(secondMemberId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));

        List<ChatRooms> resultA = chatRoomsRepository.findChatRoomsByUserAAndUserB(memberA,memberB);
        List<ChatRooms> resultB = chatRoomsRepository.findChatRoomsByUserAAndUserB(memberB,memberA);
        // 기존에 존재하는 채팅방이 있으면 새로운 채팅방 생성을 막음
        if(!resultA.isEmpty() || !resultB.isEmpty()){throw new ErrorHandler(ErrorStatus._IS_ALREADY_EXIST_CHAT_ROOM);}
        ChatRooms chatRooms = ChatRooms.builder()
                .userA(memberA)
                .userB(memberB)
                .build();
        chatRoomsRepository.save(chatRooms);
        return chatRooms.getId();
    }

    @Transactional
    public Long sendMessage(ChatMessageRequestDto.sendMessage dto, Long chatRoomId, Long senderId){
        Member sender = memberRepository.findById(senderId).orElseThrow(()->new ErrorHandler(ErrorStatus._NOT_EXIST_MEMBER));
        ChatRooms chatRooms = chatRoomsRepository.findById(chatRoomId).orElseThrow(()->new RuntimeException("채팅방 찾기 실패"));

        //해당 채팅방 소속이 아니라면 메시지 전송을 막음
        if(chatRooms.getUserA().getId() != senderId && chatRooms.getUserB().getId() != senderId){
            throw new ErrorHandler(ErrorStatus._NOT_A_MEMBER_OF_THIS_ROOM);
        }

        ChatMessages chatMessages = ChatMessages.builder()
                .chatRooms(chatRooms)
                .sender(sender)
                .message(dto.getContent())
                .build();

        chatMessageRepository.save(chatMessages);
        return chatMessages.getId();


    }
}
