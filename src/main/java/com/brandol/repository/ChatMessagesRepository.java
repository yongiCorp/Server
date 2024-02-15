package com.brandol.repository;

import com.brandol.domain.ChatMessages;
import com.brandol.domain.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {
    List<ChatMessages> findAllByChatRooms(ChatRooms cr);

    /*
    @Query(value = "SELECT MAX(ch.chat_id) " +
            " FROM chat_messages ch " +
            " INNER JOIN chat_rooms cr ON cr.chatrooms_id = ch.chat_room_chat_room_id " +
            " INNER JOIN crewpass.crew_chat_room ccr ON cr.chat_room_id = ch.chat_room_chat_room_id " +
            " WHERE cr.chat_room_id = :chatRoomId", nativeQuery = true)
    Long findLastReadChatIdByChatRoomId(@Param("chatRoomId") Long chatRoomId);

     */
}