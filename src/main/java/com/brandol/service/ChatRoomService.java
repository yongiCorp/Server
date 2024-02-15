package com.brandol.service;

import com.brandol.dto.ChatRoomDto;
import com.brandol.repository.ChatRoomsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomsRepository chatRoomsRepository;
/*


    // 채팅방 생성
    @Transactional
    public Integer createChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
        return chatRoom.getChatRoomId();
    }

    // 모집글 아이디로 채팅방 아이디 찾기
    public Integer findChatRoomIdByRecruitmentId(Integer recruitmentId) {
        ChatRoom chatRoom = chatRoomRepository.findByRecruitmentId(recruitmentId);
        return chatRoom.getChatRoomId();
    }

    // 채팅방 정보 조회
    public ChatRoomInfo findInfoByChatroomId(Integer chatroomId) {
        return chatRoomRepository.findInfoByChatroomId(chatroomId);
    }

    // 동아리 - 채팅방 리스트 조회
    public List<ChatRoomList> findChatRoomListByCrewId(Integer crewId) {
        return chatRoomRepository.findChatRoomListByCrewId(crewId);
    }
    */

    /*
    // 회원 - 채팅방 리스트 조회
    public List<ChatRoomDto> findChatRoomListByUserId(Long memberId) {
        return chatRoomsRepository.findChatRoomListByUserId(userId);
    }

     */

    /*
    // 채팅방 정보 수정 (채팅방 폐쇄 날짜 수정)
    @Transactional
    public Integer updateChatRoom(Integer recruitmentId, Timestamp closeTime, Integer isDeleted) {
        ChatRoom chatRoom = chatRoomRepository.findByRecruitmentId(recruitmentId);
        if (chatRoom == null) {
            return 0;
        }

        chatRoom.setCloseTime(closeTime);
        chatRoom.setIsDeleted(isDeleted);
        chatRoomRepository.save(chatRoom);
        return 1;
    }

    // 모집글 아이디로 채팅방 찾기
    public ChatRoom findByRecruitmentId(Integer recruitmentId) {
        return chatRoomRepository.findByRecruitmentId(recruitmentId);
    }

     */
}
