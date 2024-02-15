package com.brandol.repository;

import com.brandol.domain.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRoomRepository extends JpaRepository<ChatRooms, Integer> {
    /*
    @Query(value = "SELECT COUNT(*) FROM crewpass.user_chat_room WHERE chat_room_chat_room_id = :chatroomId", nativeQuery = true)

    Integer findLastEnterOrderByChatRoomId(@Param("chatroomId") Integer chatroomId);

    @Query(value = "SELECT enter_order FROM crewpass.user_chat_room" +
            " WHERE user_user_id = :userId AND chat_room_chat_room_id = :chatRoomId"
            , nativeQuery = true)
    Integer findEnterOrderByUserIdAndChatRoomId(@Param("userId") Integer userId, @Param("chatRoomId") Integer chatRoomId);

    @Query(value = "SELECT user_chat_room_id " +
            " FROM crewpass.user_chat_room " +
            " WHERE chat_room_chat_room_id = :chatRoomId AND user_user_id = :userId", nativeQuery = true)
    Integer findUserChatRoomIdByChatRoomIdAndUserId(@Param("chatRoomId") Integer chatRoomId, @Param("userId") Integer userId);

    // 회원 - 해당 채팅방에서 안 읽은 채팅 개수 조회
    @Query(value = "SELECT COUNT(chat_id) FROM crewpass.user_chat_room ucr " +
            " INNER JOIN crewpass.chat_room cr ON cr.chat_room_id = ucr.chat_room_chat_room_id " +
            " INNER JOIN crewpass.chat ch ON ch.chat_room_chat_room_id = ucr.chat_room_chat_room_id " +
            " WHERE ch.chat_room_chat_room_id = :chatRoomId AND ucr.user_user_id = :userId AND ch.chat_id > ucr.last_read_chat_id", nativeQuery = true)
    Integer findUnReadChatCountByUserId(@Param("chatRoomId") Integer chatroomId, @Param("userId") Integer userId);

     */
}