package com.brandol.repository;

import com.brandol.domain.ChatMessages;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessages,Long> {

    @Query("select cm from ChatMessages cm where cm.chatRooms.id = :chatRoomId and cm.id > :lastId")
    List<ChatMessages> getNewMessages(@Param("chatRoomId")Long chatRoomId, @Param("lastId")Long lastId, Pageable pageable);
}
