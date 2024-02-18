package com.brandol.service;

import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.converter.ChatConverter;
import com.brandol.domain.ChatMessages;
import com.brandol.domain.ChatRooms;
import com.brandol.domain.Member;
import com.brandol.dto.request.ChatMessageRequestDto;
import com.brandol.dto.response.ChatResponseDto;
import com.brandol.repository.ChatMessageRepository;
import com.brandol.repository.ChatRoomsRepository;
import com.brandol.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public ChatResponseDto.ChatMessagesDto getNewMessages(Long chatRoomId, Long lastMessageId,Long memberId){

        if(chatRoomsRepository.findChatRoomsByUserAId(memberId).isEmpty() && chatRoomsRepository.findChatRoomsByUserBId(memberId).isEmpty()){
            throw new ErrorHandler(ErrorStatus._NOT_A_MEMBER_OF_THIS_ROOM);
        }
        // 마지막 id 이후 인덱스 범위에서 최근 메시지 30개 조회 -> 중복 조회 방지 목적
        List<ChatMessages> chatMessagesList = chatMessageRepository.getNewMessages(chatRoomId,lastMessageId, PageRequest.of(0,30));
        List<ChatResponseDto.ChatSingleMessageDto> chatMessagesDtos = new ArrayList<>();
        for (ChatMessages chatMessages : chatMessagesList) {
            ChatResponseDto.ChatSingleMessageDto dto = ChatConverter.toChatSingleMessageDto(chatMessages);
            chatMessagesDtos.add(dto);
        }
        Long lastIndex = chatMessagesList.get(chatMessagesList.size()-1).getId(); // 배열에서 마지막 인덱스 가져오기

        return ChatConverter.toChatMessagesDto(chatMessagesDtos,lastIndex);

    }
}
